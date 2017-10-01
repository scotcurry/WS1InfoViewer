package com.curryware.ws1infoviewer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SystemHealth {

    @SerializedName("AnalyticsUrl")
    @Expose
    private String analyticsUrl;
    @SerializedName("AuditPollInterval")
    @Expose
    private String auditPollInterval;
    @SerializedName("EncryptionServiceVersion")
    @Expose
    private String encryptionServiceVersion;
    @SerializedName("AnalyticsConnectionOk")
    @Expose
    private String analyticsConnectionOk;
    @SerializedName("EncryptionServiceVerified")
    @Expose
    private String encryptionServiceVerified;
    @SerializedName("FederationBrokerStatus")
    @Expose
    private String federationBrokerStatus;
    @SerializedName("ServiceReadOnlyMode")
    @Expose
    private String serviceReadOnlyMode;
    @SerializedName("AuditWorkerThreadAlive")
    @Expose
    private String auditWorkerThreadAlive;
    @SerializedName("BuildVersion")
    @Expose
    private String buildVersion;
    @SerializedName("AuditQueueSize")
    @Expose
    private String auditQueueSize;
    @SerializedName("DatabaseStatus")
    @Expose
    private String databaseStatus;
    @SerializedName("HostName")
    @Expose
    private String hostName;
    @SerializedName("EncryptionStatus")
    @Expose
    private String encryptionStatus;
    @SerializedName("FederationBrokerOk")
    @Expose
    private String federationBrokerOk;
    @SerializedName("EncryptionConnectionOk")
    @Expose
    private String encryptionConnectionOk;
    @SerializedName("EncryptionServiceImpl")
    @Expose
    private String encryptionServiceImpl;
    @SerializedName("ClusterId")
    @Expose
    private String clusterId;
    @SerializedName("DatabaseConnectionOk")
    @Expose
    private String databaseConnectionOk;
    @SerializedName("StatusDate")
    @Expose
    private String statusDate;
    @SerializedName("MaintenanceMode")
    @Expose
    private String maintenanceMode;
    @SerializedName("MessagingConnectionOk")
    @Expose
    private String messagingConnectionOk;
    @SerializedName("fipsModeEnabled")
    @Expose
    private String fipsModeEnabled;
    @SerializedName("ServiceVersion")
    @Expose
    private String serviceVersion;
    @SerializedName("AuditQueueSizeThreshold")
    @Expose
    private String auditQueueSizeThreshold;
    @SerializedName("IpAddress")
    @Expose
    private String ipAddress;
    @SerializedName("AuditDisabled")
    @Expose
    private String auditDisabled;
    @SerializedName("AllOk")
    @Expose
    private String allOk;

    public String getAnalyticsUrl() {
        return analyticsUrl;
    }

    public void setAnalyticsUrl(String analyticsUrl) {
        this.analyticsUrl = analyticsUrl;
    }

    public String getAuditPollInterval() {
        return auditPollInterval;
    }

    public void setAuditPollInterval(String auditPollInterval) {
        this.auditPollInterval = auditPollInterval;
    }

    public String getEncryptionServiceVersion() {
        return encryptionServiceVersion;
    }

    public void setEncryptionServiceVersion(String encryptionServiceVersion) {
        this.encryptionServiceVersion = encryptionServiceVersion;
    }

    public String getAnalyticsConnectionOk() {
        return analyticsConnectionOk;
    }

    public void setAnalyticsConnectionOk(String analyticsConnectionOk) {
        this.analyticsConnectionOk = analyticsConnectionOk;
    }

    public String getEncryptionServiceVerified() {
        return encryptionServiceVerified;
    }

    public void setEncryptionServiceVerified(String encryptionServiceVerified) {
        this.encryptionServiceVerified = encryptionServiceVerified;
    }

    public String getFederationBrokerStatus() {
        return federationBrokerStatus;
    }

    public void setFederationBrokerStatus(String federationBrokerStatus) {
        this.federationBrokerStatus = federationBrokerStatus;
    }

    public String getServiceReadOnlyMode() {
        return serviceReadOnlyMode;
    }

    public void setServiceReadOnlyMode(String serviceReadOnlyMode) {
        this.serviceReadOnlyMode = serviceReadOnlyMode;
    }

    public String getAuditWorkerThreadAlive() {
        return auditWorkerThreadAlive;
    }

    public void setAuditWorkerThreadAlive(String auditWorkerThreadAlive) {
        this.auditWorkerThreadAlive = auditWorkerThreadAlive;
    }

    public String getBuildVersion() {
        return buildVersion;
    }

    public void setBuildVersion(String buildVersion) {
        this.buildVersion = buildVersion;
    }

    public String getAuditQueueSize() {
        return auditQueueSize;
    }

    public void setAuditQueueSize(String auditQueueSize) {
        this.auditQueueSize = auditQueueSize;
    }

    public String getDatabaseStatus() {
        return databaseStatus;
    }

    public void setDatabaseStatus(String databaseStatus) {
        this.databaseStatus = databaseStatus;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getEncryptionStatus() {
        return encryptionStatus;
    }

    public void setEncryptionStatus(String encryptionStatus) {
        this.encryptionStatus = encryptionStatus;
    }

    public String getFederationBrokerOk() {
        return federationBrokerOk;
    }

    public void setFederationBrokerOk(String federationBrokerOk) {
        this.federationBrokerOk = federationBrokerOk;
    }

    public String getEncryptionConnectionOk() {
        return encryptionConnectionOk;
    }

    public void setEncryptionConnectionOk(String encryptionConnectionOk) {
        this.encryptionConnectionOk = encryptionConnectionOk;
    }

    public String getEncryptionServiceImpl() {
        return encryptionServiceImpl;
    }

    public void setEncryptionServiceImpl(String encryptionServiceImpl) {
        this.encryptionServiceImpl = encryptionServiceImpl;
    }

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }

    public String getDatabaseConnectionOk() {
        return databaseConnectionOk;
    }

    public void setDatabaseConnectionOk(String databaseConnectionOk) {
        this.databaseConnectionOk = databaseConnectionOk;
    }

    public String getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(String statusDate) {
        this.statusDate = statusDate;
    }

    public String getMaintenanceMode() {
        return maintenanceMode;
    }

    public void setMaintenanceMode(String maintenanceMode) {
        this.maintenanceMode = maintenanceMode;
    }

    public String getMessagingConnectionOk() {
        return messagingConnectionOk;
    }

    public void setMessagingConnectionOk(String messagingConnectionOk) {
        this.messagingConnectionOk = messagingConnectionOk;
    }

    public String getFipsModeEnabled() {
        return fipsModeEnabled;
    }

    public void setFipsModeEnabled(String fipsModeEnabled) {
        this.fipsModeEnabled = fipsModeEnabled;
    }

    public String getServiceVersion() {
        return serviceVersion;
    }

    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    public String getAuditQueueSizeThreshold() {
        return auditQueueSizeThreshold;
    }

    public void setAuditQueueSizeThreshold(String auditQueueSizeThreshold) {
        this.auditQueueSizeThreshold = auditQueueSizeThreshold;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getAuditDisabled() {
        return auditDisabled;
    }

    public void setAuditDisabled(String auditDisabled) {
        this.auditDisabled = auditDisabled;
    }

    public String getAllOk() {
        return allOk;
    }

    public void setAllOk(String allOk) {
        this.allOk = allOk;
    }

}
