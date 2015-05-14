namespace KipsuFi

open WebSharper.Html.Server
open WebSharper
open WebSharper.Sitelets
open System.Web

module Site =

    let Main =
        Sitelet.Infer <| function
            | Main -> Skin.MainPage
            | Algorithms id -> (Api.Algorithm <| HttpUtility.UrlDecode(id))
            | Datastructures id -> (Api.Datastructure <| HttpUtility.UrlDecode(id))
            | _ -> Content.ServerError
       
    let Algo = 
        Sitelet.Folder "algorithms" [
            Sitelet.Content "/" ListAlgorithms (Api.Algorithms())
            Sitelet.Content "" ListAlgorithms (Api.Algorithms())
        ]

    let Data = 
        Sitelet.Folder "datastructures" [
            Sitelet.Content "/" ListDatastructures (Api.Datastructures())
            Sitelet.Content "" ListDatastructures (Api.Datastructures())
        ]

[<Sealed>]
type Website() =
    interface IWebsite<Action> with
        member this.Sitelet =
            Sitelet.Sum[
                Site.Algo
                Site.Data
                Site.Main
            ]
        member this.Actions = []

type Global() =
    inherit System.Web.HttpApplication()

    member g.Application_Start(sender: obj, args: System.EventArgs) =
        ()

[<assembly: Website(typeof<Website>)>]
do ()
