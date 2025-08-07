package api.lab3.service;

import api.lab3.response.OccupationResponse;

public interface OccupationService {
    public OccupationResponse getOccupations();

    public int getSystemConfigValue(String moduleCode, String configName);
}
