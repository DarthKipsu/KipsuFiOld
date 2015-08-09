[![Build Status](https://travis-ci.org/DarthKipsu/KipsuFi.svg)](https://travis-ci.org/DarthKipsu/KipsuFi)
[![Dependencies Status](http://jarkeeper.com/DarthKipsu/KipsuFi/status.svg)](http://jarkeeper.com/DarthKipsu/KipsuFi)

KipsuFi

My personal website. Supports different kinds of documents (projects, articles, algorithms and datastructures currently) and can display lists of the documents and show each document on it's own page.

The site uses Clojure server and a postgresql with HTML for content.

## REST API

All single page content is saved in postgresql database in html format and can be accessed through REST API. Lists of documents are generated from the same database content automatically. The api supports queries for articles, projects, algorithms and datastructures.

Responses are in JSON format. Currently the API does not support adding or changing content, but this is planned for future. Right now to add or change content, you need to edit the database directly through console.

To get a lits of all entries simply navigate to:  
```
//[server]/api
```

A list of 10 most recent articles of any type: 
```
//[server]/api/recent
```

List of all articles, projects, algorithms or datastructures can be accessed by:
```
//[server]/api/articles  
//[server]/api/projects  
//[server]/api/algorithms  
//[server]/api/datastructures
```

A single entry:  
```
//[server]/api/articles/[article name]
//[server]/api/projects/[projects name]
//[server]/api/algorithms/[algorithm name]
//[server]/api/datastructures/[datastructure name]
```

##Example

My website [darth.kipsu.fi](http://darth.kipsu.fi) is running the latest version of the application.

## License

Distributed under the GNU General Public License v2.0
