public class PowerUp extends Entity {
    private boolean PresentOnLevel;
    private int PresenceCounter;
    private boolean Active;
    private int ActiveCounter;


    public PowerUp(String mark, Coordinates coordinates, Coordinates escapeCoordinates, Direction direction, boolean presentOnLevel, int presenceCounter, boolean active, int activeCounter) {
        super(mark, coordinates, escapeCoordinates, direction);
        PresentOnLevel = presentOnLevel;
        PresenceCounter = presenceCounter;
        Active = active;
        ActiveCounter = activeCounter;
    }

    public boolean isPresentOnLevel() {
        return PresentOnLevel;
    }

    public void setPresentOnLevel(boolean PresentOnLevel) {
        this.PresentOnLevel = PresentOnLevel;
    }

    public void setPresentOnLevel() {
        this.PresentOnLevel = !PresentOnLevel;
    }

    public int getPresenceCounter() {
        return PresenceCounter;
    }

    public void setPresenceCounter() {
        this.PresenceCounter++;
    }
    public void setPresenceCounter(int setPresenceCounter) {
        this.PresenceCounter = setPresenceCounter;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        this.Active = active;
    }

    public int getActiveCounter() {
        return ActiveCounter;
    }

    public void setActiveCounter() {
        this.ActiveCounter++;
    }

    public void setActiveCounter(int setActiveCounter) {
        this.ActiveCounter = setActiveCounter;
    }
}
