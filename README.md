[![Build Status](https://travis-ci.org/DarthKipsu/KipsuFi.svg)](https://travis-ci.org/DarthKipsu/KipsuFi)
[![Dependencies Status](http://jarkeeper.com/DarthKipsu/KipsuFi/status.svg)](http://jarkeeper.com/DarthKipsu/KipsuFi)

KipsuFi

My personal website, with Clojure.

## REST API

At the moment the api supports queries for algorithms and datastructures. Responses are in JSON format.

To get a lits of all entries simply navigate to:  
```
//[server]/api
```

List of all algorithms or datastructures can be accessed by:
```
//[server]/api/algorithms  
//[server]/api/datastructures
```

A single entry:  
```
//[server]/api/algorithms/[algorithm name]
//[server]/api/datastructures/[datastructure name]
```

## License

Distributed under the GNU General Public License v2.0
