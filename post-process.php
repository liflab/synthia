<?php

/* Replace all occurrences of {@docRoot} by an actual folder name
 * in all HTML files */
$base_dir = "docs/javadoc/";
$doc_root = ".";
$handle = opendir($base_dir);
while (false !== ($entry = readdir($handle)))
{
	if (is_dir($entry) || !preg_match("/\\.html$/", $entry))
		continue;
	echo $entry."\n";
	$contents = file_get_contents($base_dir.$entry);
	$contents = str_replace("{@docRoot}", $doc_root, $contents);
	file_put_contents($base_dir.$entry, $contents);

}
closedir($handle);

/* Copy all contents of src/doc-files into docs/javadoc/doc-files */
xcopy("Source/Core/src/doc-files", "docs/javadoc/doc-files");
xcopy("Source/Examples/src/doc-files", "docs/javadoc/doc-files");

/* Create a .nojekyll file at the root of the docs folder. This is to solve
   https://help.github.com/articles/files-that-start-with-an-underscore-are-missing/ */
file_put_contents($base_dir.".nojekyll", "");

/**
 * Copy a file, or recursively copy a folder and its contents
 * @author      Aidan Lister <aidan@php.net>
 * @version     1.0.1
 * @link        http://aidanlister.com/2004/04/recursively-copying-directories-in-php/
 * @param       string   $source    Source path
 * @param       string   $dest      Destination path
 * @param       int      $permissions New folder creation permissions
 * @return      bool     Returns true on success, false on failure
 */
function xcopy($source, $dest, $permissions = 0755)
{
    // Check for symlinks
    if (is_link($source)) {
        return symlink(readlink($source), $dest);
    }

    // Simple copy for a file
    if (is_file($source)) {
        return copy($source, $dest);
    }

    // Make destination directory
    if (!is_dir($dest)) {
        mkdir($dest, $permissions);
    }

    // Loop through the folder
    $dir = dir($source);
    while (false !== $entry = $dir->read()) {
        // Skip pointers
        if ($entry == '.' || $entry == '..') {
            continue;
        }

        // Deep copy directories
        xcopy("$source/$entry", "$dest/$entry", $permissions);
    }

    // Clean up
    $dir->close();
    return true;
}
?>
