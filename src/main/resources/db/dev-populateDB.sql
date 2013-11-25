INSERT INTO data_custodians (id, description, client_id, url) VALUES (1, 'ConEdison (localhost)', 'data_custodian', 'http://localhost:8080/DataCustodian');

INSERT INTO application_information (uuid, dataCustodianId, dataCustodianThirdPartyId, thirdPartyApplicationName, dataCustodianThirdPartySecret, dataCustodianDefaultScopeResource) VALUES ('550e8400-e29b-41d4-a716-4466554413a0', 'data_custodian', 'third_party', 'ConEdison (localhost)', 'secret', 'http://localhost:8080/DataCustodian/RetailCustomer/ScopeSelectionList');
INSERT INTO application_information_scopes (application_information_id, scope) VALUES (1, 'FB=4_5_15;IntervalDuration=3600;BlockDuration=monthly;HistoryLength=13');
INSERT INTO application_information_scopes (application_information_id, scope) VALUES (1, 'FB=4_5_16;IntervalDuration=3600;BlockDuration=monthly;HistoryLength=13');
