package view;

import java.awt.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

import controller.EventController;
import model.Direction;
import model.IReadableNode;
import model.Position;
import model.RoomAttribute;

final class NodePanel extends JPanel {
  private List<List<IReadableNode>> nodes;
  private EventController listener;

  NodePanel() {
    super();
    this.setBackground(Color.BLACK);

    nodes = new ArrayList<>();

  }

  void setNodes(List<List<IReadableNode>> nodes) {
    this.nodes = nodes;

  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    for (List<IReadableNode> nodeList : this.nodes) {
      for (IReadableNode node : nodeList) {

        Position nodePos = this.adaptPos(node.getPosition());

        //gets the images
        Image roomImage = null;
        Image typeImage = null;
        try {
          roomImage = this.selectHallwayImage(node);
          typeImage = this.selectTypeImage(node);
        } catch (IOException e) {
          e.printStackTrace();
        }


        //Draw the images if the player has been there
        if (node.beenVisited()) {
          g2d.drawImage(roomImage,nodePos.getCol(),nodePos.getRow(), null);
          if (typeImage != null) {
            g2d.drawImage(typeImage,nodePos.getCol(), nodePos.getRow(),null);
          }
          this.drawRoomAttributes(node, g2d);
        }


      }
    }


  }

  private Position adaptPos(Position position) {
    return new Position(position.getRow() * 64, position.getCol() * 64);
  }

  public void setListener(EventController listener) {
    this.listener = listener;
  }

  private Image selectTypeImage(IReadableNode node) throws IOException{
    Image typeImage = null;


      switch (node.getRoomType()) {

        case WUMPUS:
          typeImage= ImageIO.read(new File("reasources/wumpus.png"));
          break;
        case START:
          typeImage = ImageIO.read(new File("reasources/startRoom.png"));
          break;
        case SUPERBAT:
          typeImage= ImageIO.read(new File("reasources/bats.png"));
          break;
        case PIT:
          typeImage= ImageIO.read(new File("reasources/pit.png"));
          break;
        case SUPERBAT_AND_PIT:
          typeImage= ImageIO.read(new File("reasources/pitbat.png"));
          break;
        default:
          break;
      }
    return typeImage;


  }

  private Image selectHallwayImage(IReadableNode node) throws IOException {
    List<Direction> directions = new ArrayList<>(node.getConnectedDirs());
    directions.sort(Comparator.comparingInt(Enum::ordinal));
    StringBuilder imageName = new StringBuilder();
    imageName.append("reasources/");
    for (Direction dir : directions) {
      imageName.append(dir.toString().charAt(0));
    }
    imageName.append(".png");
    return ImageIO.read(new File(imageName.toString()));

  }

  private void drawRoomAttributes(IReadableNode node, Graphics2D graphics2D) {

    List<RoomAttribute> attributes = new ArrayList<>(node.getRoomAttributes());
    attributes.sort(Comparator.comparingInt(Enum::ordinal));
    Position nodePos = this.adaptPos(node.getPosition());
    for (RoomAttribute attribute : attributes) {
      StringBuilder attributeFileName = new StringBuilder();
      attributeFileName.append("reasources/");
      switch (attribute) {

        case NEXT_TO_WUMPUS:
          attributeFileName.append("stench");
          break;
        case NEXT_TO_PIT:
          attributeFileName.append("breeze");
          break;
        case HAS_PLAYER1:
          attributeFileName.append("player1");
          break;
        case HAS_PLAYER2:

          attributeFileName.append("player2");
          break;
      }
      attributeFileName.append(".png");

      Image attributeImg = null;
      try {
        attributeImg = ImageIO.read(new File(attributeFileName.toString()));
      } catch (IOException e) {
        throw new IllegalStateException("File Missing");
      }
      graphics2D.drawImage(attributeImg,nodePos.getCol(), nodePos.getRow(),null);
    }
  }
}


