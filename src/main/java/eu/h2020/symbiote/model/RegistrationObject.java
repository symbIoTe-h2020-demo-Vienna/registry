package eu.h2020.symbiote.model;

/**
 * Created by mateuszl on 03.10.2016.
 */
public class RegistrationObject {
    String registrationObjectBody;
    RegistrationObjectType type;
    String parentID;

    public RegistrationObject(String registrationObjectBody, RegistrationObjectType type, String parentID) {
        this.registrationObjectBody = registrationObjectBody;
        this.type = type;
        this.parentID = parentID;
    }

    public String getRegistrationObjectBody() {
        return registrationObjectBody;
    }

    public void setRegistrationObjectBody(String registrationObjectBody) {
        this.registrationObjectBody = registrationObjectBody;
    }

    public RegistrationObjectType getType() {
        return type;
    }

    public void setType(RegistrationObjectType type) {
        this.type = type;
    }

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

}
