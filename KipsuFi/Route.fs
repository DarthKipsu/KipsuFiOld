namespace KipsuFi

type Action =
    | [<CompiledName "">] Main
    | [<CompiledName "listAlgorithms">] ListAlgorithms
    | [<CompiledName "algorithms">] Algorithms of id: string
    | [<CompiledName "listDatastructures">] ListDatastructures
    | [<CompiledName "datastructures">] Datastructures of id: string
