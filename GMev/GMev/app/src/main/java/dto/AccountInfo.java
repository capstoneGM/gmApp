package dto;

import java.util.Map;

public class AccountInfo {
    public AccountInfo(Map<String, Object> map) {
        fullName = (String) map.get("fullName");
        email = (String) map.get("email");
        phone = (String) map.get("phone");
        address = (String) map.get("address");
        vehicleModel = (String) map.get("vehicleModel");
        vehicleYear = (String) map.get("vehicleYear");
        vehicleIdNumber = (String) map.get("vehicleIdNumber");
        accountBalance = (Long) map.get("balance");
    }

    public String fullName;
    public String email;
    public String phone;
    public String address;
    public String vehicleModel;
    public String vehicleYear;
    public String vehicleIdNumber;
    public Long accountBalance;
}
