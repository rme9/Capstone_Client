package edu.uwf.scavenger;

public class Building {

    public String building_id;
    public String _id;
    public LocationInfo info;

    public Building()
    {

    }

    public Building(String building_id, LocationInfo info, String _id)
    {
        this.building_id = building_id;
        this.info = info;
        this._id = _id;

        info.room = _id.substring(3);
    }

    public String getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(String building_id) {
        this.building_id = building_id;
    }

    public String getLocation_type() {
        return info.Location_type;
    }

    public void setLocation_type(String location_type) {
        info.Location_type = location_type;
    }

    public String getRoom() {
        return _id.substring(3);
    }

    public void setRoom(String room) {
        this.info.room = room;
    }

    public String getDescription() {
        return info.Description;
    }

    public void setDescription(String description) {
        info.Description = description;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public class LocationInfo
    {
        public String Location_type;
        public String room;
        public String Description;

        public LocationInfo (String Location_type, String room, String Description)
        {
            this.Location_type = Location_type;
            this.room = room;
            this.Description = Description;
        }

        public LocationInfo()
        {}

    }
}

