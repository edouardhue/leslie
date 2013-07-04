leslie
======

A rotating MediaWiki client

Usage
=====

```
java leslie.Main
 -c FILE : Credentials file
 -p FILE : Source pages file
 -s FILE : State file
 -t VAL  : Target page
 -w URL  : MediaWiki API endpoint
 -e VAL  : Edit comment
```

Files
-----

Leslie uses three different files :

+ Credentials in a Java properties format with thow keys : **username** and **password** ;
+ A list of page names to use as sources, one per line ;
+ A state file containing the index (beginning at **0**) of the last successfully used source page.

If the state file is missing, a warning is issued and the file is initialized with the value **0**. The other two files must be present.

Other parameters
----------------

+ The **target page** parameter is the name of the page to be overwritten with the content of the so-called source pages, at each run ;
+ The **API endpoint** is described here : https://www.mediawiki.org/wiki/User_agent#The_endpoint ;
+ An optional edit comment.
