package de.htwg.se.stratego.util

enum Step (val step: String):
  case UNDO extends Step("UNDO")
  case REDO extends Step("REDO")

class UndoManager :
  private var undoStack: List[Command]= Nil
  private var redoStack: List[Command]= Nil
  def doStep(command: Command) = 
    undoStack = command::undoStack
    command.doStep

  def step(step: Step) = 
    if(step == Step.UNDO)
      undoStack match 
        case  Nil =>
        case head::stack => 
          head.undoStep
          undoStack=stack
          redoStack= head::redoStack
    else
      redoStack match 
        case Nil =>
        case head::stack => 
          head.redoStep
          redoStack=stack
          undoStack=head::undoStack

  def undoStep  = step(Step.UNDO)
    
  def redoStep = step(Step.REDO)
    