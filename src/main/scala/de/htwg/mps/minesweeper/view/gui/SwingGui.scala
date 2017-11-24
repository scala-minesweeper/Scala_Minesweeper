package de.htwg.mps.minesweeper.view.gui

import java.awt.{Color, Font}
import javax.swing.border.LineBorder

import de.htwg.mps.minesweeper.controller._

import scala.swing.event.Key
import scala.swing.{Action, BorderPanel, FlowPanel, Frame, GridPanel, Label, Menu, MenuBar, MenuItem, TextField}

class SwingGui(controller: IGameController) extends Frame {

  listenTo(controller)

  title = "Minesweeper"

  var status = new TextField("Initializing", 20)

  contents = new BorderPanel {
    add(gridPanel, BorderPanel.Position.North)
    add(status, BorderPanel.Position.South)
  }

  visible = true

  reactions += {
    case _: FieldChanged => redraw()
    case _: GameStart =>
      status.text = "Game is running"
      resize()
    case _: GameWon =>
      status.text = "You win"
    case _: GameLost =>
      status.text = "You lost"
  }

  menuBar = new MenuBar {
    contents += new Menu("File") {
      mnemonic = Key.F
      contents += new MenuItem(Action("New") {
        controller.restartGame()
      })
      contents += new MenuItem(Action("Quit") {
        System.exit(0)
      })
    }
  }

  private def redraw(): Unit = resize()

  private def resize(): Unit = contents = new BorderPanel {
    add(gridPanel, BorderPanel.Position.North)
    add(status, BorderPanel.Position.South)
  }

  private def gridPanel: GridPanel = new GridPanel(controller.getGrid.getSize._1, controller.getGrid.getSize._1) {
    border = new LineBorder(Color.BLACK, 2)
    background = Color.WHITE
    for {
      outerRow <- 0 until controller.getGrid.getSize._1
      outerColumn <- 0 until controller.getGrid.getSize._1
    } {
      contents += new FlowPanel() {
        contents += new Label() {
          text = controller.getGrid.get(outerRow, outerColumn).getOrElse().toString
          font = new Font("Verdana", 1, 36)
        }
      }
    }
  }
}
