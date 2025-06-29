package Entity;

import java.math.BigDecimal;
import java.util.Date;

public class Payslip {
    // Employee Details
    private String employeeName;
    private String employeeId;
    private BigDecimal payRate;
    private String tinNumber;
    private String philhealthNumber;
    private String sssNumber;
    private String pagibigNumber;
    private String department;
    private String employmentStatus;

    // Pay Period Details
    private String payPeriod;
    private Date payDate;

    // Work Details
    private BigDecimal daysWorked;
    private BigDecimal lateAmount;
    private BigDecimal basicPay;
    private BigDecimal ndAmount;
    private BigDecimal sunSpHol;
    private BigDecimal legHoliday;
    private BigDecimal otAmount;

    // Deductions
    private BigDecimal sssDeduction;
    private BigDecimal philhealthDeduction;
    private BigDecimal pagibigDeduction;
    private BigDecimal efundDeduction;
    private BigDecimal otherDeductions;

    // Adjustments and Compensations
    private BigDecimal salaryAdjustment;
    private BigDecimal allowanceAdjustment;
    private BigDecimal otherCompensation;

    // Totals
    private BigDecimal grossIncome;
    private BigDecimal totalDeduction;
    private BigDecimal totalCompensation;
    private BigDecimal netPay;

    // Constructor
    public Payslip(String employeeName, String employeeId, BigDecimal payRate, String tinNumber,
                   String philhealthNumber, String sssNumber, String pagibigNumber, String department,
                   String employmentStatus, String payPeriod, Date payDate, BigDecimal daysWorked,
                   BigDecimal lateAmount, BigDecimal basicPay, BigDecimal ndAmount, BigDecimal sunSpHol,
                   BigDecimal legHoliday, BigDecimal otAmount, BigDecimal sssDeduction,
                   BigDecimal philhealthDeduction, BigDecimal pagibigDeduction, BigDecimal efundDeduction,
                   BigDecimal otherDeductions, BigDecimal salaryAdjustment, BigDecimal allowanceAdjustment,
                   BigDecimal otherCompensation, BigDecimal grossIncome, BigDecimal totalDeduction,
                   BigDecimal totalCompensation, BigDecimal netPay) {
        this.employeeName = employeeName;
        this.employeeId = employeeId;
        this.payRate = payRate;
        this.tinNumber = tinNumber;
        this.philhealthNumber = philhealthNumber;
        this.sssNumber = sssNumber;
        this.pagibigNumber = pagibigNumber;
        this.department = department;
        this.employmentStatus = employmentStatus;
        this.payPeriod = payPeriod;
        this.payDate = payDate;
        this.daysWorked = daysWorked;
        this.lateAmount = lateAmount;
        this.basicPay = basicPay;
        this.ndAmount = ndAmount;
        this.sunSpHol = sunSpHol;
        this.legHoliday = legHoliday;
        this.otAmount = otAmount;
        this.sssDeduction = sssDeduction;
        this.philhealthDeduction = philhealthDeduction;
        this.pagibigDeduction = pagibigDeduction;
        this.efundDeduction = efundDeduction;
        this.otherDeductions = otherDeductions;
        this.salaryAdjustment = salaryAdjustment;
        this.allowanceAdjustment = allowanceAdjustment;
        this.otherCompensation = otherCompensation;
        this.grossIncome = grossIncome;
        this.totalDeduction = totalDeduction;
        this.totalCompensation = totalCompensation;
        this.netPay = netPay;
    }

    // Getters
    public String getEmployeeName() { return employeeName; }
    public String getEmployeeId() { return employeeId; }
    public BigDecimal getPayRate() { return payRate; }
    public String getTinNumber() { return tinNumber; }
    public String getPhilhealthNumber() { return philhealthNumber; }
    public String getSssNumber() { return sssNumber; }
    public String getPagibigNumber() { return pagibigNumber; }
    public String getDepartment() { return department; }
    public String getEmploymentStatus() { return employmentStatus; }
    public String getPayPeriod() { return payPeriod; }
    public Date getPayDate() { return payDate; }
    public BigDecimal getDaysWorked() { return daysWorked; }
    public BigDecimal getLateAmount() { return lateAmount; }
    public BigDecimal getBasicPay() { return basicPay; }
    public BigDecimal getNdAmount() { return ndAmount; }
    public BigDecimal getSunSpHol() { return sunSpHol; }
    public BigDecimal getLegHoliday() { return legHoliday; }
    public BigDecimal getOtAmount() { return otAmount; }
    public BigDecimal getSssDeduction() { return sssDeduction; }
    public BigDecimal getPhilhealthDeduction() { return philhealthDeduction; }
    public BigDecimal getPagibigDeduction() { return pagibigDeduction; }
    public BigDecimal getEfundDeduction() { return efundDeduction; }
    public BigDecimal getOtherDeductions() { return otherDeductions; }
    public BigDecimal getSalaryAdjustment() { return salaryAdjustment; }
    public BigDecimal getAllowanceAdjustment() { return allowanceAdjustment; }
    public BigDecimal getOtherCompensation() { return otherCompensation; }
    public BigDecimal getGrossIncome() { return grossIncome; }
    public BigDecimal getTotalDeduction() { return totalDeduction; }
    public BigDecimal getTotalCompensation() { return totalCompensation; }
    public BigDecimal getNetPay() { return netPay; }
}
