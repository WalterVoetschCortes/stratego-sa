package de.htwg.se.stratego.model.matchFieldComponent.matchFieldBaseImpl
import de.htwg.se.stratego.model.matchFieldComponent.MatchFieldInterface
import de.htwg.se.stratego.model.playerComponent.Player

case class Game(var playerA: Player, var playerB: Player, size: Int, var matchField: MatchFieldInterface) :
  var bList = playerA.characterList
  var rList = playerB.characterList

  def init(bIdx: Int, rIdx: Int, row: Int, col: Int, currentMatchField: MatchFieldInterface): MatchFieldInterface = 
    if(row < size)
      if currentMatchField.fields.field(row,col).isSet then
        return currentMatchField
      if isBlueField(row) then
        matchField = matchField.addChar(row, col, bList(bIdx),Colour.FigureCol(0))
        if col < matchField.fields.matrixSize - 1 then init(bIdx + 1, rIdx, row, col + 1, currentMatchField)
        else init(bIdx + 1, rIdx, row + 1, 0, currentMatchField)
      else if isRedField(row) then
        matchField = matchField.addChar(row, col, rList(rIdx),Colour.FigureCol(1))
        if col < matchField.fields.matrixSize - 1 then init(bIdx, rIdx + 1, row, col + 1, currentMatchField)
        else init(bIdx, rIdx + 1, row + 1, 0, currentMatchField)
      else init(bIdx, rIdx, row + 1, col, currentMatchField)
    return matchField

  def characValue(charac:String): Int = 
    if charac.matches("[1-9]") then
      return charac.toInt
    charac match 
      case "B" => 11
      case "M" => 10
      case "F" => 0
    
  def checkField(checkBlue:Boolean)(row:Int): Boolean = 
    if(checkBlue)
      matchField.fields.matrixSize match 
        case 4 | 5 => if(row > 0 && checkBlue) false else true
        case 6 | 7 => if(row > 1 && checkBlue) false else true
        case 8 | 9 => if(row > 2 && checkBlue) false else true
        case 10    => if(row > 3 && checkBlue) false else true
      
    else 
      matchField.fields.matrixSize match 
        case 4 => if(row < 3 && !checkBlue) false else true
        case 5 | 6 => if(row < 4 && !checkBlue) false else true
        case 7 | 8 => if(row < 5 && !checkBlue) false else true
        case 9 | 10 => if(row < 6 && !checkBlue) false else true        
  
  def isBlueField(row:Int): Boolean = checkField(true)(row)

  def isRedField(row:Int): Boolean = checkField(false)(row)

  def setField(setBlue: Boolean)(row:Int, col:Int, charac: String): MatchFieldInterface = 
    if (isBlueChar(charac) && isBlueField(row) || isRedChar(charac) && isRedField(row)) && !matchField.fields.field(row,col).isSet then
      val idx = if(setBlue) bList.indexOf(GameCharacter(Figure.FigureVal(charac,characValue(charac)))) else rList.indexOf(GameCharacter(Figure.FigureVal(charac,characValue(charac))))
      matchField = if(setBlue) matchField.addChar(row,col,bList(idx),Colour.FigureCol(0)) else matchField.addChar(row,col,rList(idx),Colour.FigureCol(1))
      if (setBlue) bList = bList.patch(idx, Nil, 1) else rList = rList.patch(idx, Nil, 1)
      return matchField
    matchField

  def setBlue(row:Int, col:Int, charac: String): MatchFieldInterface = setField(true)(row, col, charac)

  def setRed(row:Int, col:Int, charac: String): MatchFieldInterface = setField(false)(row, col, charac)

  def set(player: Int, row:Int, col:Int, charac: String): MatchFieldInterface = 
    player match
      case 0 => return setBlue(row, col, charac)
      case 1 => return setRed(row, col, charac)
    matchField

  def isChar(checkBlue: Boolean)(charac:String): Boolean = 
    if(checkBlue) bList.map(GameCharacter => if(GameCharacter.figure.name.equals(charac)) return true else false) else rList.map(GameCharacter => if(GameCharacter.figure.name.equals(charac)) return true else false) 
    false

  def isRedChar(charac:String): Boolean = isChar(false)(charac)

  def isBlueChar(charac:String): Boolean = isChar(true)(charac)

  def onlyBombAndFlag(board: MatchFieldInterface, currentPlayerIndex: Int): Boolean = 
    for 
      row <- 0 until board.fields.matrixSize
      col <- 0 until board.fields.matrixSize 
    do
      if board.fields.field(row,col).isSet && board.fields.field(row,col).colour.get.value==currentPlayerIndex then
        if board.fields.field(row, col).character.get.figure.value == 1 ||
          board.fields.field(row, col).character.get.figure.value == 2 ||
          board.fields.field(row, col).character.get.figure.value == 3 ||
          board.fields.field(row, col).character.get.figure.value == 4 ||
          board.fields.field(row, col).character.get.figure.value == 5 ||
          board.fields.field(row, col).character.get.figure.value == 6 ||
          board.fields.field(row, col).character.get.figure.value == 7 ||
          board.fields.field(row, col).character.get.figure.value == 8 ||
          board.fields.field(row, col).character.get.figure.value == 9 ||
          board.fields.field(row, col).character.get.figure.value == 10 then
          return false
    true

  def setPlayers(input: String): List[Player] = 
    input.split(" ").map(_.trim).toList match
      case player1 :: player2 :: Nil =>
        playerA = playerA.copy(player1, bList)
        playerB = playerA.copy(player2, rList)
        val playerList = List[Player](playerA,playerB)
        playerList

  def move(direction: Char, matchField: MatchFieldInterface, row: Int, col: Int, currentPlayerIndex: Int): MatchFieldInterface = 
    if matchField.fields.field(row,col).isSet.equals(true) && matchField.fields.field(row,col).colour.get.value == currentPlayerIndex then
      direction match 
        case 'u' => return moveUp(matchField, row, col)
        case 'd' => return moveDown(matchField, row, col)
        case 'r' => return moveRight(matchField, row, col)
        case 'l' => return moveLeft(matchField, row, col)
    matchField

  def moveDir(rowCol:Int, eqRowCol:Int, rowPos:Int, colPos:Int, newRowPos: Int, newColPos:Int)(matchField: MatchFieldInterface, row: Int, col: Int): MatchFieldInterface = 
    if (rowCol == eqRowCol || matchField.fields.field(rowPos, colPos).isSet.equals(true) || isFlagOrBomb(matchField, row, col)) matchField
    else matchField.removeChar(row, col).addChar(newRowPos, newColPos, matchField.fields.field(row, col).character.get, matchField.fields.field(row, col).colour.get)
  
  def moveDown(matchField: MatchFieldInterface, row: Int, col: Int): MatchFieldInterface = moveDir(row, size-1, row+1, col, row+1, col)(matchField, row, col)

  def moveUp(matchField: MatchFieldInterface, row: Int, col: Int): MatchFieldInterface = moveDir(row, 0, row-1, col, row-1, col)(matchField, row, col)

  def moveRight(matchField: MatchFieldInterface, row: Int, col: Int): MatchFieldInterface = moveDir(col, size-1, row, col+1, row, col+1)(matchField, row,col)

  def moveLeft(matchField: MatchFieldInterface, row: Int, col: Int): MatchFieldInterface = moveDir(col, 0, row, col-1, row, col-1)(matchField, row, col)

  def figureHasValue(matchF: MatchFieldInterface, row: Int,col: Int): Int = matchF.fields.field(row,col).character.get.figure.value

  def isFlagOrBomb(matchField: MatchFieldInterface, row: Int,col: Int): Boolean = if matchField.fields.field(row,col).character.get.figure.value == 0 ||
    matchField.fields.field(row,col).character.get.figure.value == 11 then true else false

  object Context extends Game(playerA: Player, playerB: Player, size: Int, matchField: MatchFieldInterface) 
    def attack(matchField: MatchFieldInterface, rowA: Int, colA: Int, rowD: Int, colD: Int, currentPlayerIndex: Int): MatchFieldInterface = 
      def strategy1:MatchFieldInterface = matchField
      def strategy3:MatchFieldInterface = matchField.removeChar(rowD, colD).addChar(rowD, colD,
        matchField.fields.field(rowA,colA).character.get,matchField.fields.field(rowA,colA).colour.get).removeChar(rowA,colA)
      def strategy6:MatchFieldInterface = matchField.removeChar(rowD, colD)
      def strategy7:MatchFieldInterface = matchField.removeChar(rowA, colA)
      def strategy8:MatchFieldInterface = matchField.removeChar(rowA, colA).removeChar(rowD, colD)

      val fieldIsSet = if(matchField.fields.field(rowA, colA).isSet.equals(false) ||
        matchField.fields.field(rowD, colD).isSet.equals(false)) return strategy1
      val attackIsValid = if(matchField.fields.field(rowD,colD).colour.get.value == currentPlayerIndex &&
        matchField.fields.field(rowA,colA).colour.get.value == currentPlayerIndex) return strategy1
      val enemyAttackIsInValid = if(matchField.fields.field(rowD,colD).colour.get.value != currentPlayerIndex &&
        matchField.fields.field(rowA,colA).colour.get.value != currentPlayerIndex) return strategy1
      val wrongPlayerAttack = if(matchField.fields.field(rowD,colD).colour.get.value == currentPlayerIndex &&
        matchField.fields.field(rowA,colA).colour.get.value != currentPlayerIndex) return strategy1
      val attackToFarAway = if(((Math.abs(rowA-rowD)>1)||(Math.abs(colA-colD)>1))||((Math.abs(rowA-rowD)==1) &&
        (Math.abs(colA-colD)==1))) return strategy1
      val isFlagOrBomb = if(matchField.fields.field(rowA,colA).character.get.figure.value == 0 ||
        matchField.fields.field(rowA,colA).character.get.figure.value == 11) return strategy1
      val minerAttackTheBomb = if(figureHasValue(matchField, rowA, colA) == 3 &&
        figureHasValue(matchField, rowD, colD) == 11) return strategy6
      val spyAttackMarshal = if((figureHasValue(matchField, rowA,colA) == 1) &&
        (figureHasValue(matchField, rowD, colD) == 10)) return strategy3
      val defenceIsStronger = if (figureHasValue(matchField, rowA,colA) < figureHasValue(matchField,rowD, colD)) return strategy7
      val attackIsStronger = if(figureHasValue(matchField, rowA,colA) > figureHasValue(matchField,rowD, colD)) return strategy3
      val attackEqualsDefence = if(figureHasValue(matchField, rowA,colA) == figureHasValue(matchField,rowD, colD)) return strategy8
      matchField

