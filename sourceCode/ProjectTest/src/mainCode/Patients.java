package mainCode;


import java.time.LocalDate;

public class Patients extends Users {

    String p_ID, patientFName, patientLName, p_email, p_phone, phys_Name, radTech_Name, op_Time, or_Status;
    LocalDate date;


    public Patients(String p_ID, String p_FName, String p_LName, String p_email, String p_phone) {
        this.p_ID = p_ID;
        this.patientFName = p_FName;
        this.patientLName = p_LName;
        this.p_email = p_email;
        this.p_phone = p_phone;
    }

    public Patients(String patientFName, String patientLName) {
        this.patientFName = patientFName;
        this.patientLName = patientLName;
    }

    public Patients(String patientFName, String patientLName, String phys_Name, LocalDate date, String op_Time) {
        this.patientFName = patientFName;
        this.patientLName = patientLName;
        this.phys_Name = phys_Name;
        this.date = date;
        this.op_Time = op_Time;
    }

    public Patients(LocalDate date, String op_Time, String patientFName, String patientLName, String phys_Name, String radTech_Name, String or_Status) {
        this.date = date;
        this.op_Time = op_Time;
        this.patientFName = patientFName;
        this.patientLName = patientLName;
        this.phys_Name = phys_Name;
        this.radTech_Name = radTech_Name;
        this.or_Status = or_Status;
    }



    public String getP_ID() {
        return p_ID;
    }

    public void setP_ID(String p_ID) {
        this.p_ID = p_ID;
    }

    public String getPatientFName() {
        return patientFName;
    }

    public void setPatientFName(String p_FName) {
        this.patientFName = p_FName;
    }

    public String getPatientLName() {
        return patientLName;
    }

    public void setPatientLName(String p_LName) {
        this.patientLName = p_LName;
    }

    public String getP_email() {
        return p_email;
    }

    public void setP_email(String p_email) {
        this.p_email = p_email;
    }

    public String getP_phone() {
        return p_phone;
    }

    public void setP_phone(String p_phone) {
        this.p_phone = p_phone;
    }



    public String getPhys_Name() {
        return phys_Name;
    }

    public void setPhys_Name(String phys_Name) {
        this.phys_Name = phys_Name;
    }

    public String getOp_Time() {
        return op_Time;
    }

    public void setOp_Time(String op_Time) {
        this.op_Time = op_Time;
    }

    public String getOr_Status() {
        return or_Status;
    }

    public void setOr_Status(String or_Status) {
        this.or_Status = or_Status;
    }

    public String getRadTech_Name() {
        return radTech_Name;
    }

    public void setRadTech_Name(String radTech_Name) {
        this.radTech_Name = radTech_Name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Patients{" +
                "patientFName='" + patientFName + '\'' +
                ", patientLName='" + patientLName + '\'' +
                '}';
    }
}