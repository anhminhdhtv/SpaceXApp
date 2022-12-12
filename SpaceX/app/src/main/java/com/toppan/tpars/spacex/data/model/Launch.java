package com.toppan.tpars.spacex.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Launch {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("date_utc")
    private Date date;

    @SerializedName("rocket")
    private String rocket;

    private List<Core> cores;

    public Launch(String id, String name, Date date, String rocket, List<Core> cores) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.rocket = rocket;
        this.cores = cores;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRocket() {
        return rocket;
    }

    public void setRocket(String rocket) {
        this.rocket = rocket;
    }

    public List<Core> getCores() {
        return cores;
    }

    public void setCores(List<Core> cores) {
        this.cores = cores;
    }

    public class Core {
        private String core;
        private String flight;
        private boolean gridfins;
        private boolean legs;
        private boolean reused;

        public Core(String core, String flight, boolean gridfins, boolean legs, boolean reused) {
            this.core = core;
            this.flight = flight;
            this.gridfins = gridfins;
            this.legs = legs;
            this.reused = reused;
        }

        public String getCore() {
            return core;
        }

        public void setCore(String core) {
            this.core = core;
        }

        public String getFlight() {
            return flight;
        }

        public void setFlight(String flight) {
            this.flight = flight;
        }

        public boolean isGridfins() {
            return gridfins;
        }

        public void setGridfins(boolean gridfins) {
            this.gridfins = gridfins;
        }

        public boolean isLegs() {
            return legs;
        }

        public void setLegs(boolean legs) {
            this.legs = legs;
        }

        public boolean isReused() {
            return reused;
        }

        public void setReused(boolean reused) {
            this.reused = reused;
        }

        @Override
        public String toString() {
            return "Core: " + (core == null ? "" : core) + '\n' +
                    "Flight: " + (flight == null ? "" : flight) + "              " +
                    "Gridfins: " + (gridfins ? "Yes" : "No") + '\n' +
                    "Legs: " + (legs ? "Yes" : "No") + "           " +
                    "Reused: " + (reused ? "Yes" : "No");
        }
    }
}
