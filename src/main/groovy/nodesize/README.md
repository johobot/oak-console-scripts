# Node Size Writer

Writes the approximate size of nodes above a given threshold to a file. The
script only considers STRING properties. The default threshold is 16,00,000 bytes (16 mb)
and can be adjusted with the system property `-DbiggerThan`.


Load url for the script

    java -jar oak-run*.jar console /path/to/segmentstore ":load https://raw.githubusercontent.com/johobot/oak-console-scripts/master/src/main/groovy/nodesize/writeNodeSizeAndPath.groovy"

* Read only access
* Does not requires DataStore access
* See [readme](../../../../README.md#usage) for usage details 
