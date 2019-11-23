Synthia: synthesize data structures in Java
===========================================

Synthia is a flexible generator of data structures. With Synthia, it is
possible to generate numbers, strings, lists, sentences from a predefined
grammar, random walks in a finite-state machine, or any other user-defined
object. More importantly, all these basic "generators" can be combined to
create complex structures and behaviors.

Synthia can be used to generate test inputs for
[fuzzing](https://en.wikipedia.org/wiki/Fuzzing), create a
[test stub](https://en.wikipedia.org/wiki/Test_stub) that provides more
flexible responses than simple "canned answers", or create a semi-realistic
simulation of a system's output, such as a log.

Examples
--------

Here are simple examples of the basic building blocks to synthesize
things with Synthia. The
[Examples](https://github.com/liflab/synthia/tree/master/Source/Examples)
folder has more complex examples, such as simulating multiple users visiting
a web site.

### Generate a string from generated parts

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

The Markov chain created by this block of code looks like this:

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

AntRun is a template structure for Java projects. Through its comprehensive
Ant build script, it supports automated execution of unit tests, generation
of [Javadoc](http://www.oracle.com/technetwork/articles/java/index-jsp-135444.html)
documentation and code coverage reports (with
[JaCoCo](http://www.eclemma.org/jacoco/)), and download and installation
of JAR dependencies as specified in an external, user-definable XML file.
It also includes a boilerplate `.gitignore` file suitable for an Eclipse
project.

All this is done in a platform-independent way, so your build scripts
should work on both MacOS, Linux and Windows.

Table of Contents                                                    {#toc}
-----------------

- [Quick start guide](#quickstart)
- [Available tasks](#tasks)
- [Continuous integration](#ci)
- [Cross-compiling](#xcompile)
- [About the author](#about)

Quick start guide                                             {#quickstart}
-----------------

1. First make sure you have the following installed:

  - The Java Development Kit (JDK) to compile. AntRun was developed and
    tested on version 6 and 7 of the JDK, but it is probably safe to use
    any later version.
  - [Ant](http://ant.apache.org) to automate the compilation and build
    process

2. Download the AntRun template from
   [GitHub](https://github.com/sylvainhalle/AntRun) or clone the repository
   using Git:
   
   git@github.com:sylvainhalle/AntRun.git

3. Override any defaults, and specify any dependencies your project
   requires by editing `config.xml`. In particular, you may want
   to change the name of the Main class.

4. Start writing your code in the `Source/Core` folder, and your unit
   tests in `Source/CoreTest`. Optionally, you can create an Eclipse
   workspace out of the `Source` folder, with `Core` and `CoreTest` as
   two projects within this workspace.

5. Use Ant to build your project. To compile the code, generate the
   Javadoc, run the unit tests, generate a test and code coverage report
   and bundle everything in a runnable JAR file, simply type `ant` (without
   any arguments) on the command line.
   
6. If dependencies were specified in step 4 and are not present in the
   system, type `ant download-deps`, followed by `ant install-deps` to
   automatically download and install them before compiling. The latter
   command might require to be run as administrator --the way to do this
   varies according to your operating system (see below).

Otherwise, use one of the many [tasks](#tasks) that are predefined.

Available tasks                                                    {#tasks}
---------------

This document is incomplete. Execute

    $ ant -p

from the project's top folder to get the list of all available targets.

### dist

The default task. Currently applies `jar`.

### compile

Compiles the project.

### compile-tests

Compiles the unit tests.

### jar

Compiles the project, generates the Javadoc and creates a runnable JAR,
including the sources and the documentation (and possibly the project's
dependencies, see `download-deps` below).

### test

Performs tests with jUnit and generates code coverage report with JaCoCo.
The unit test report (in HTML format) is available in the `test/junit`
folder (which will be created if it does not exist). The code coverage
report is available in the `test/coverage` folder.

### download-deps

Downloads all the JAR dependencies declared in `config.xml`, and required
to correctly build the project. The JAR files are extracted and placed in
the `dep` or the `lib` folder. When compiling (with the `compile` task), the
compiler is instructed to include these JARs in its classpath. Depending on the
setting specified in `config.xml`, these JARs are also bundled in the
output JAR file of the `jar` task.

### download-rt6

Downloads the bootstrap classpath (`rt.jar`) for Java 6, and places it in
the project's root folder. See [cross-compiling](#xcompile).

Continuous integration                                               {#ci}
----------------------

AntRun makes it easy to use [continuous
integration](https://en.wikipedia.org/wiki/Continuous_integration) services
like [Travis CI](https://travis-ci.org) or
[Semaphore](http://semaphoreapp.com). The sequence of commands to
automatically setup the environment, build and test it is (for Linux):

    $ ant download-deps
    $ ant dist test

The second command must be run as administrator, as it copies the required
dependencies into a system folder that generally requires that access. For
Windows systems, running as administrator is done with the
[`runas` command](https://technet.microsoft.com/en-us/library/cc771525.aspx#BKMK_examples).

Notice how, apart from the call to `sudo`, all the process is
platform-independent.

Declaring dependencies                                              {#deps}
----------------------

Among other configuration settings, dependencies can be declared in the file
`config.xml`. Locate the `<dependencies>` section in that file, and add as
many `<dependency>` entries as required. The structure of such a section is as
follows:

``` xml
<dependency>
      <name>Test Dep</name>
      <classname>ca.uqac.lif.NonExistentClass</classname>
      <files>
        <jar>http://sylvainhalle.github.io/AntRun/placeholders/dummy-jar.jar</jar>
        <zip>http://sylvainhalle.github.io/AntRun/placeholders/dummy-zip.zip</zip>
      </files>
      <bundle>true</bundle>
</dependency>
```

The parameters are:

- `name`: a human-readable name for the dependency, only used for display
- `classname`: a fully qualified class name that is supposed to be provided
  by the dependency. AntRun checks if this class name is present in the
  classpath; if not, it will download the files specified in the `files`
  section
- `files`: a list of either `jar` or `zip` elements, each containing a URL to
  a JAR file, or an archive of JAR files. AntRun downloads these files and
  places them in either the `dep` or the `lib` folders of the project (both are
  in the classpath). If the URL is a zip, it also unzips the content of the
  archive.
- `bundle`: when this element has the value `true`, the dependency is copied
  to the `dep` folder; otherwise, it is copied to the `lib` folder. As was
  said, both are in the classpath, but only the JARs in the `dep` folder are
  bundled when creating a JAR file for the project (using the `jar` task).

Cross-compiling                                                 {#xcompile}
---------------

The `.class` files are marked with the major version number of the compiler
that created them; hence a file compiled with JDK 1.7 will contain this
version number in its metadata. A JRE 1.6 will refuse to run them,
regardless of whether they were built from 1.6-compliant code.
*Cross-compiling* is necessary if one wants to make a project compatible
with a version of Java earlier than the one used to compile it. 

By default, AntRun compiles your project using the default JDK installed on
your computer. However, you can compile files that are compatible with
a specific version of Java by putting the *bootstrap* JAR file `rt.jar`
that corresponds to that version in the project's root folder (i.e. in the
same folder as `build.xml`). When started, AntRun checks for the presence
bootstrap JAR; if present, it uses it instead of the system's bootstrap
classpath.

For example, if one downloads the `rt.jar` file from JDK 1.6 (using
the `download-rt6` task), the compiled files will be able to be run by
a Java 6 virtual machine. (Assuming the code itself is Java 6-compliant.)

Projects that use AntRun                                        {#projects}
------------------------

Virtually every Java project developed at [LIF](http://liflab.ca) uses
an AntRun template project. This includes:

- [Azrael](https://github.com/sylvainhalle/Azrael), a generic serialization
  library
- [BeepBeep 3](https://liflab.github.io/beepbeep-3), an event stream
  processing engine, and most of its
  [palettes](https://github.com/liflab/beepbeep-3-palettes)
- [Bullwinkle](https://github.com/sylvainhalle/Bullwinkle), a runtime BNF
  parser
- [Jerrydog](https://github.com/sylvainhalle/Jerrydog), a lightweight web
  server
- [LabPal](https://liflab.github.io/labpal), a framework for running
  computer experiments
- [TeXtidote](https://github.com/sylvainhalle/textidote), a spelling and
  grammar checker for LaTeX documents

...and more.

About the author                                                   {#about}
----------------

AntRun was written by [Sylvain Hallé](http://leduotang.ca/sylvain),
Full Professor at [Université du Québec à
Chicoutimi](http://www.uqac.ca/), Canada.
