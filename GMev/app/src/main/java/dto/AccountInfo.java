package dto;

import java.util.Map;

public class AccountInfo {
    public AccountInfo(Map<String, String> map) {
        fullName = map.get("fullName");
        email = map.get("email");
        phone = map.get("phone");
        address = map.get("address");
        vehicleModel = map.get("vehicleModel");
        vehicleYear = map.get("vehicleYear");
        vehicleIdNumber = map.get("vehicleIdNumber");
    }

    public String fullName;
    public String email;
    public String phone;
    public String address;
    public String vehicleModel;
    public String vehicleYear;
    public String vehicleIdNumber;
}
