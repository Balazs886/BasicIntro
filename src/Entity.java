public class Entity {
    private String mark;
    private Coordinates coordinates;
    private Coordinates escapeCoordinates;
    private Direction direction;

    public Entity(String mark, Coordinates coordinates, Coordinates escapeCoordinates, Direction direction) {
        this.mark = mark;
        this.coordinates = coordinates;
        this.escapeCoordinates = escapeCoordinates;
        this.direction = direction;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Coordinates getEscapeCoordinates() {
        return escapeCoordinates;
    }

    public void setEscapeCoordinates(Coordinates escapeCoordinates) {
        this.escapeCoordinates = escapeCoordinates;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
