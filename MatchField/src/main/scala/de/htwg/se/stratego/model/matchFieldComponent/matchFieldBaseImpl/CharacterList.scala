package de.htwg.se.stratego.model.matchFieldComponent.matchFieldBaseImpl

case class CharacterList(size: Int):
  var characterList = Seq[GameCharacter]()

  size match 
    case 10 =>
      characterList ++= Seq(GameCharacter(Figure.Scout),
        GameCharacter(Figure.Bomb),
        GameCharacter(Figure.Scout),
        GameCharacter(Figure.Bomb),
        GameCharacter(Figure.Miner),
        GameCharacter(Figure.Bomb),
        GameCharacter(Figure.Sergeant),
        GameCharacter(Figure.General),
        GameCharacter(Figure.Sergeant),
        GameCharacter(Figure.Colonel),
        GameCharacter(Figure.Major),
        GameCharacter(Figure.Lieutenant),
        GameCharacter(Figure.Major),
        GameCharacter(Figure.Captain),
        GameCharacter(Figure.Lieutenant),
        GameCharacter(Figure.Bomb),
        GameCharacter(Figure.Scout),
        GameCharacter(Figure.Captain),
        GameCharacter(Figure.Lieutenant),
        GameCharacter(Figure.Major),
        GameCharacter(Figure.Lieutenant),
        GameCharacter(Figure.Sergeant),
        GameCharacter(Figure.Bomb),
        GameCharacter(Figure.Marshal),
        GameCharacter(Figure.Sergeant),
        GameCharacter(Figure.Miner),
        GameCharacter(Figure.Miner),
        GameCharacter(Figure.Bomb),
        GameCharacter(Figure.Miner),
        GameCharacter(Figure.Miner),
        GameCharacter(Figure.Scout),
        GameCharacter(Figure.Captain),
        GameCharacter(Figure.Scout),
        GameCharacter(Figure.Scout),
        GameCharacter(Figure.Captain),
        GameCharacter(Figure.Scout),
        GameCharacter(Figure.Colonel),
        GameCharacter(Figure.Scout),
        GameCharacter(Figure.Spy),
        GameCharacter(Figure.Flag))

    case 9 => characterList ++= Seq(GameCharacter(Figure.Bomb),
      GameCharacter(Figure.Bomb),
      GameCharacter(Figure.Bomb),
      GameCharacter(Figure.Bomb),
      GameCharacter(Figure.Marshal),
      GameCharacter(Figure.General),
      GameCharacter(Figure.Colonel),
      GameCharacter(Figure.Major),
      GameCharacter(Figure.Major),
      GameCharacter(Figure.Captain),
      GameCharacter(Figure.Captain),
      GameCharacter(Figure.Lieutenant),
      GameCharacter(Figure.Lieutenant),
      GameCharacter(Figure.Sergeant),
      GameCharacter(Figure.Sergeant),
      GameCharacter(Figure.Sergeant),
      GameCharacter(Figure.Miner),
      GameCharacter(Figure.Miner),
      GameCharacter(Figure.Miner),
      GameCharacter(Figure.Miner),
      GameCharacter(Figure.Scout),
      GameCharacter(Figure.Scout),
      GameCharacter(Figure.Scout),
      GameCharacter(Figure.Scout),
      GameCharacter(Figure.Scout),
      GameCharacter(Figure.Spy),
      GameCharacter(Figure.Flag))

    case 8 => characterList ++= Seq(GameCharacter(Figure.Bomb),
      GameCharacter(Figure.Bomb),
      GameCharacter(Figure.Bomb),
      GameCharacter(Figure.Marshal),
      GameCharacter(Figure.General),
      GameCharacter(Figure.Colonel),
      GameCharacter(Figure.Major),
      GameCharacter(Figure.Major),
      GameCharacter(Figure.Captain),
      GameCharacter(Figure.Captain),
      GameCharacter(Figure.Lieutenant),
      GameCharacter(Figure.Lieutenant),
      GameCharacter(Figure.Sergeant),
      GameCharacter(Figure.Sergeant),
      GameCharacter(Figure.Miner),
      GameCharacter(Figure.Miner),
      GameCharacter(Figure.Miner),
      GameCharacter(Figure.Scout),
      GameCharacter(Figure.Scout),
      GameCharacter(Figure.Scout),
      GameCharacter(Figure.Scout),
      GameCharacter(Figure.Scout),
      GameCharacter(Figure.Spy),
      GameCharacter(Figure.Flag))

    case 7 => characterList ++= Seq(GameCharacter(Figure.Bomb),
      GameCharacter(Figure.Bomb),
      GameCharacter(Figure.Marshal),
      GameCharacter(Figure.General),
      GameCharacter(Figure.Colonel),
      GameCharacter(Figure.Major),
      GameCharacter(Figure.Captain),
      GameCharacter(Figure.Lieutenant),
      GameCharacter(Figure.Sergeant),
      GameCharacter(Figure.Miner),
      GameCharacter(Figure.Scout),
      GameCharacter(Figure.Scout),
      GameCharacter(Figure.Spy),
      GameCharacter(Figure.Flag))

    case 6 => characterList ++= Seq(GameCharacter(Figure.Bomb),
      GameCharacter(Figure.Miner),
      GameCharacter(Figure.General),
      GameCharacter(Figure.Colonel),
      GameCharacter(Figure.Major),
      GameCharacter(Figure.Captain),
      GameCharacter(Figure.Lieutenant),
      GameCharacter(Figure.Sergeant),
      GameCharacter(Figure.Marshal),
      GameCharacter(Figure.Scout),
      GameCharacter(Figure.Spy),
      GameCharacter(Figure.Flag))

    case 5 => characterList ++= Seq(GameCharacter(Figure.General),
      GameCharacter(Figure.Colonel),
      GameCharacter(Figure.Major),
      GameCharacter(Figure.Captain),
      GameCharacter(Figure.Flag))

    case 4 => characterList ++= Seq(GameCharacter(Figure.General),
      GameCharacter(Figure.Colonel),
      GameCharacter(Figure.Captain),
      GameCharacter(Figure.Flag))
  
  def getCharacterList(): Seq[GameCharacter] = 
    characterList
  

