INSERT INTO delivery_cost_rule (text_name, text_type, num_sort_order, text_criteria_operator, num_criteria_value, num_cost, nflag_active) VALUES
('Reject', 'WEIGHT', 10, '>', 50, NULL, 1),
('Heavy Parcel', 'WEIGHT', 20, '>', 10, 20, 1),
('Small Parcel', 'VOLUME', 30, '<', 1500, .03, 1),
('Medium Parcel', 'VOLUME', 40, '<', 2500, .04, 1),
('Large Parcel', 'VOLUME', 50, '<', 3500, .05, 1);