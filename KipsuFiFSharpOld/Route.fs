namespace KipsuFi

type Action =
    | [<CompiledName "">] Main
    | ListAlgorithms
    | [<CompiledName "algorithms">] Algorithms of id: string
    | ListDatastructures
    | [<CompiledName "datastructures">] Datastructures of id: string