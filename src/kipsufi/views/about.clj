(ns kipsufi.views.about)

(def title "About")

(defn wrapper []
  [:div.content
   [:div.img-bg [:img {:src "images/faces_big/about.png" :class "face-big"}]]
   [:h1.title "About Me"]
   [:div.print
    [:div.col-md-12
     [:p.intro "I'm Verna, a first year computer sciences student at the Univeristy of Helsinki. In December 2013 I started my journy as a padawan programmer. I'm constantly thriving to be better as a programmer as well as a person. I want to create well crafted, clean and functional code."]]
    [:div.col-md-6.small-font
     [:p]
     [:p [:strong "Name: "] "Verna Koskinen"]
     [:p [:strong "Email: "] "dath.kipsu@gmail.com"]
     [:p [:strong "Phone: "] "+358451277921"]
     [:p [:strong "Address: "] "Kurkimäentie 19 D 36, 00940 Helsinki, Finland"]]
    [:div.col-md-6.small-font
     [:p [:strong "Website: "] [:a {:href "//darth.kipsu.fi"} "darth.kipsu.fi"]]
     [:p [:strong "GitHub: "] [:a {:href "//github.com/DarthKipsu"} "Darth Kipsu"]]
     [:p [:strong "LinkedIn: "] [:a {:href "//www.linkedin.com/pub/verna-koskinen/85/339/a4a"} "Verna Koskinen"]]
     [:p [:strong "Facebook: "] [:a {:href "//www.facebook.com/verna.koskinen"} "Verna Koskinen"]]]
    [:div.col-md-12.space]
    [:div.col-md-12
     [:p "I have a previous degree in environmental sciences, but begun studying programming just before Christmas in 2013 and completely fell for it! I'm still exploring the field and having fun learning new things. Seems like in programming, learning new can never stop - how fun is that! I like problem solving and math, so this is definitely \"my thing\"."]
     [:p "In my free time, when not learning about programming, I like to get outside with my dog and spouse and go walking in the forest, hiking or cycling. Nature is really important to me and I think caring for the environment and spending time in the nature is one of the best ways to keep ones mind at ease."]]
    [:div.col-md-12.space]]
   [:div.img-bg [:img {:src "images/faces_big/about_site.png" :class "face-big"}]]
   [:h1.title.black "About this site"]
   [:div.print
    [:div.col-md-12
     [:p "This site is fully created with " [:a {:href "//clojure.org/"} "Clojure"] " and " [:a {:href "//github.com/clojure/clojurescript"} "ClojureScript"] ", using " [:a {:href "//lesscss.org/"} "Less"] " for stylesheets. I went with Clojure as I wanted to learn more about creating websites with a functional approach."]
     [:p [:strong "Frontend"] " is created with " [:a {:href "//facebook.github.io/react/"} "React"] ", using " [:a {:href "https://github.com/reagent-project/reagent"} "Reagent"] " ClojureScript interface. React was chosen because at the time I had a project at work using React and I wanted to learn more about it at my free time aswell. Reagent is a great interface for ClojureScript as it's minimalistic, functional and mostly uses ClojureScript functions, so the syntax is much like writing plain Clojure or ClojureScript."]
     [:p [:strong "Backend"] " is run by Clojure and uses basic " [:a {:href "//github.com/ring-clojure"} "Ring"] " and " [:a {:href "//github.com/weavejester/compojure"} "Compojure"] " combination to  create a REST API with the site content to serve for the UI. You can access the API from " [:a {:href "//darth.kipsu.fi/api"} "darth.kipsu.fi/api"] ". The site content is stored in a " [:a {:href "//postgresql.org/"} "PostgreSQL"] " database, mainly as text fields containing HTML. Currently changes to the database need to be handwritten from the console, but I'm planning on improving the API and creating an interface for it in the near future."]
     [:p "All code for the site is open source and can be found from the GitHub repo here: " [:a {:href "//github.com/DarthKipsu/KipsuFi"} "github.com/DarthKipsu/KipsuFi"]]]
    [:div.col-md-12.space]]])
