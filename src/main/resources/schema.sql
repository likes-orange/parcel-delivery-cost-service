DROP TABLE IF EXISTS delivery_cost_rule;

CREATE TABLE delivery_cost_rule (
  id_delivery_cost_rule INT AUTO_INCREMENT  PRIMARY KEY,
  text_type VARCHAR(250) NOT NULL,
  text_name VARCHAR(250) NOT NULL,
  num_sort_order INT NOT NULL,
  text_criteria_operator VARCHAR(250) NOT NULL,
  num_criteria_value DECIMAL NOT NULL,
  num_cost DECIMAL DEFAULT NULL,
  nflag_active INT NOT NULL
);
