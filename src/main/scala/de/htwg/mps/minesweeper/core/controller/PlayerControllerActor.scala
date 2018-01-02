package de.htwg.mps.minesweeper.core.controller

import akka.actor.{Actor, ActorRef}
import de.htwg.mps.minesweeper.api.events._
import de.htwg.mps.minesweeper.api.{GameResult, Player}
import de.htwg.mps.minesweeper.core.model.player.MinesweeperPlayer

class PlayerControllerActor(publisher: ActorRef) extends Actor {

  publisher ! RegisterPublisher

  override def receive: Receive = run(MinesweeperPlayer())

  private def run(player: Player): Receive = {
    case GameWon(game) =>
      game.getScore.fold()(score => context.become(addGameResult(player, score)))
    case GameLost(game) =>
      game.getScore.fold()(score => context.become(addGameResult(player, score)))
    case GetCurrentStatus() => sender() ! PlayerUpdate(player)
  }

  private def addGameResult(player: Player, gameResult: GameResult) = {
    val newPlayer = player.addGameResult(gameResult)
    publisher ! PlayerUpdate(newPlayer)
    run(newPlayer)
  }

}
