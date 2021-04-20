package view;


import java.util.List;

import model.IReadableNode;

public class SwingMazeViewOLd extends AMazeView {
  private SwingMazeView mainFrame;

  public SwingMazeViewOLd() {
    this.mainFrame = new SwingMazeView();
  }


  @Override
  public void animate() {
    this.mainFrame.animate();
  }
  @Override
  public void setNodes(List<List<IReadableNode>> nodes) {
    this.mainFrame.setNodes(nodes);

  }

  @Override
  public void animateGameOver() {

  }

  @Override
  public void displayError(String error) {

  }
}
