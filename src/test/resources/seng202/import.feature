#Author: George Hampton
Feature: Import Data
  @Empty_small
  Scenario: Import small data file to empty application
    Given No records are in the application
    When A file is imported with 100 valid lines
    Then The application should have 100 records stored
    And The application should have 100 records active
  
  @Empty_large
  Scenario: Import large data file to empty application
  	Given No records are in the application
  	When A file is imported with 200000 valid lines
  	Then The application should have 200000 records stored
    And The application should have 200000 records active
  	
  @Empty_invalid
  Scenario: Import invalid data file to empty application
  	Given No records are in the application
  	When A file is imported with 0 valid lines
  	Then The application should have 0 records stored
    And The application should have 0 records active
  	
  @Populated
  Scenario: Import (small) data file to populated application
    Given There are records in the application
    And There are 0 active filters
    When A file is imported with 100 valid lines
    Then The application should have 100 records stored
    And The application should have 100 records active