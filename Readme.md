Synthia: synthesize data structures in Java
===========================================

![Synthia logo](Logo.jpg?raw=true)

[![Build Status](https://app.travis-ci.com/liflab/synthia.svg?branch=master)](https://app.travis-ci.com/liflab/synthia)

[![YouTube video](https://img.youtube.com/vi/r2knLhZI5vM/0.jpg)](https://www.youtube.com/watch?v=r2knLhZI5vM)

Synthia is a flexible generator of data structures. With Synthia, it is
possible to generate numbers, strings, lists, sentences from a predefined
grammar, random walks in a finite-state machine, or any other user-defined
object. More importantly, all these basic "generators" can be combined to
create complex structures and behaviors.

Synthia comes with a library of dozens of
[code examples](https://liflab.github.io/synthia/javadoc/group___examples.html)
illustrating what can be done with it.
Among other things can be used to generate test inputs for
[fuzzing](https://en.wikipedia.org/wiki/Fuzzing), create a
[test stub](https://en.wikipedia.org/wiki/Test_stub) that provides more
flexible responses than simple "canned answers", or create a semi-realistic
simulation of a system's output, such as a log.

Among the features in Synthia:

- All sources of "choice" in the various generators are passed as arguments
  in the form of `Picker` objects. Pickers can be random, but they can also
  do something else, such as always returning the same value (special type
  `Contstant`), or cycling through a user-defined list of values (special type
  `Playback`). Users can define their own pickers, and they can return any
  type of object, not just numbers.
- The outputs of pickers can be wrapped into a `Record` object, which remembers
  the successive values they produce. These values can be replayed through
  a `Playback` picker. Since generators make choices only through pickers,
  this makes it possible to regenerate any random object. This feature is
  useful e.g. for debugging, by running a program on a specific input causing
  a failure.

Examples
--------

Here are simple examples of the basic building blocks to synthesize
things with Synthia. The
[Examples](https://github.com/liflab/synthia/tree/master/Source/Examples)
folder has more complex examples, such as simulating multiple users visiting
a web site.

### Generate primitive objects

The `ca.uqac.lif.synthia.random` package provides basic `Picker` objects that
can generate primitive values selected with a uniform probability distribution.

```java
RandomInteger ri = new RandomInteger(2, 10); // uniform random int between 2 and 10
RandomFloat rf = new RandomFloat(); // uniform random float between 0 and 1
RandomBoolean rb = new RandomBoolean(0.7); // coin toss returning true 7 out of 10 times
```

All random pickers implement methods `setSeed()` (to specify the starting seed
of their internal random source) and `reset()` (to reset their internal random
source to the initial seed value), making it possible to reproduce the random
sequences they generate.

Random strings can also be generated (using `RandomStringUtils` from
[Apache Commons Lang](http://commons.apache.org/proper/commons-lang/javadocs/api-release/index.html)
in the background):

```java
// Generate strings of length 10
RandomString rs1 = new RandomString(new Constant(10));
// Generates strings of length randomly selected in the interval 2-10
RandomString rs2 = new RandomString(new RandomInteger(2, 10));
```

This last example shows that the input to a Picker may itself be another Picker.
For `rs2`, the length of the string is determined by the result of a random
pick in the interval [2,10].

Similarly, the `Tick` picker generates an increasing sequence of numbers:

```java
Tick t1 = new Tick(10, 1);
Tick t2 = new Tick(new RandomInteger(5, 15), new RandomFloat(0.5, 1.5));
```

Here, `t1` will generate a sequence starting at 10 and incrementing by 1 on every
call to `pick()` --that is, this object is completely deterministic. In contrast,
`t2` wil generate a sequence starting at a randomly selected value between 5 and
15, and incrementing each time by a randomly selected value between 0.5 and 1.5.

### Generate a string from generated parts

The `StringPattern` picker can produce character strings where some parts are
determined by the output of other pickers.

```java
StringPattern pat = new StringPattern(
  "{$0} {$1} - Source: {$2}, Destination: {$3}, Duration: {$4}",
  new Tick(),
  new Freeze<String>(new RandomString(new RandomInteger(3, 6))), 
  new Freeze<String>(new IpAddressProvider(new RandomInteger(0, 256))), 
  new IpAddressProvider(new RandomInteger(0, 256)),
  new RandomInteger(10, 1000)));
for (int i = 0; i < 10; i++)
  System.out.println(gp.pick());
```

Produces an output like:

```
0.73096776 3dF - Source: 187.212.61.155, Destination: 187.212.61.155, Duration: 340
1.5624087 3dF - Source: 187.212.61.155, Destination: 163.79.140.29, Duration: 368
1.8029451 3dF - Source: 187.212.61.155, Destination: 152.200.85.64, Duration: 689
```

Notice the use of the `Freeze` picker: it asks for the input of another picker once,
and then returns that value forever.

### Generate sequences from a Markov chain

A [Markov chain](https://en.wikipedia.org/wiki/Markov_chain)
is a stochastic model describing a sequence of possible events.
In the following example, we define a Markov chain by creating
states (numbers 0-4), associate each state with a `Picker` object
(here all pickers generate a different string), and set the probabilities
of transitioning from a state to another.

```java
Tick tick = new Tick(0, new Enumerate<Integer>(1, 2, 1));
MarkovChain<String> mc = new MarkovChain<String>(new RandomFloat());
mc.add(0, new Constant<String>(""));
mc.add(1, new Constant<String>("START"));
mc.add(2, new StringPattern("{$0},A", tick));
mc.add(3, new StringPattern("{$0},B", tick));
mc.add(4, new StringPattern("{$0},C", tick));
mc.add(0, 1, 1).add(1, 2, 0.9).add(1, 4, 0.1)
  .add(2, 3, 1).add(3, 2, 0.7).add(3, 4, 0.3)
  .add(4, 4, 1);
for (int i = 0; i < 8; i++)
	System.out.println(mc.pick());
```

Note how the generators for states 2-3-4 share the same `tick` source,
ensuring that each string contains an incrementing "timestamp".
The Markov chain created by this block of code looks like this:

![Markov chain](https://raw.githubusercontent.com/liflab/synthia/master/Source/Examples/src/doc-files/Markov.png)

The program produces an output like this one:

```
START
1.0,A
3.0,B
4.0,A
5.0,B
7.0,C
8.0,C
```

### Generate sentences from a grammar

Synthia can produce randomly generated expressions from a formal grammar
contained in an instance of a
[Bullwinkle](https://github.com/sylvainhalle/Bullwinkle)
BNF parser.

```java
BnfParser parser = ... // A Bullwinkle parser for an arbitrary grammar
GrammarSentence p = new GrammarSentence(parser, new RandomIndex());
for (int i = 0; i < 10; i++)
  System.out.println(gp.pick());
```

With the grammar defined [here](https://github.com/liflab/synthia/blob/master/Source/Examples/src/grammar/grammar.bnf),
you get an output that looks like:

``` 
The funny old grey fox plays with the ugly big brown siamese .  
The young white cats play with the ugly small old grey siamese .
The grey birds watch the ugly old brown bird .
...
```

Building Synthia
----------------

The library is structured using the [AntRun](https://github.com/sylvainhalle/AntRun)
build scripts. Please see the AntRun Readme file for instructions on
compiling.

Generating the Javadoc
----------------------

The documentation for this repository is generated by
[Doxygen](http://doxygen.org). The configuration file `Doxyfile` contains the
appropriate settings for reading the source files and generating HTML in the
`docs` folder. On the command line, you should be able to run:

    $ doxygen Doxyfile

However, these files must be post-processed a little. Run the PHP script
`post-process.php` afterwards. When re-generating the documentation, it may
also be wise to first wipe the contents of the `docs` folder.

It is *not* recommended to use `javadoc` to generate the HTML documentation.
Several features (such as cross-referenced source code) will be missing from
the output.

About the name
--------------

Synthia is a play on "*synth*esizing" data structures.

About the authors
-----------------

Synthia is being developed by all the folks of
[Laboratoire d'informatique formelle](https://liflab.ca) at
[Université du Québec à Chicoutimi](http://www.uqac.ca), Canada.
The project lead is Sylvain Hallé, professor at Université
du Québec à Chicoutimi, Canada.

<!-- :maxLineLen=76: -->
