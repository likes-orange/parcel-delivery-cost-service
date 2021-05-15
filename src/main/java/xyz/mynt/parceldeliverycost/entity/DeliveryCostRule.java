package xyz.mynt.parceldeliverycost.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.mynt.parceldeliverycost.constant.RuleType;

import javax.persistence.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "delivery_cost_rule")
public class DeliveryCostRule {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "delivery_cost_rule_generator")
    @SequenceGenerator(name = "delivery_cost_rule_generator", sequenceName = "delivery_cost_rule_seq", allocationSize = 1)
    @Column(name = "id_delivery_cost_rule", unique = true, nullable = false, updatable = false)
    private Integer id;

    @Column(name = "text_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private RuleType type;

    @Column(name = "text_name", nullable = false)
    private String name;

    @Column(name = "num_sort_order", nullable = false)
    private Integer sortOrder;

    @Column(name = "text_criteria_operator", nullable = false)
    private String criteriaOperator;

    @Column(name = "num_criteria_value", nullable = false)
    private Double criteriaValue;

    @Column(name = "num_cost")
    private Double cost;

    @Column(name = "nflag_active", nullable = false)
    private Boolean active;

}
