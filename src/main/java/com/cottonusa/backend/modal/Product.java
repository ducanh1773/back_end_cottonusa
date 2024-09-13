package com.cottonusa.backend.modal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
@Entity
@Getter @Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nameProduct;
    private long priceProduct;
    private String summary;
    private String cover;
    private String sku;
    private String description;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<CartItem> cartItems;
    // Nếu `category_id` là khóa ngoại
    private Long category_id;  // Hoặc sử dụng @ManyToOne nếu cần

    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;

    @Temporal(TemporalType.TIMESTAMP)
    private Date deleted_at;

    private String img_product;

    public Product() {
        // Constructor mặc định
    }

    public Product(Long id, String nameProduct, long priceProduct, String summary, String cover, String sku, String description, Long category_id, Date created_at, Date deleted_at, String img_product) {
        this.id = id;
        this.nameProduct = nameProduct;
        this.priceProduct = priceProduct;
        this.summary = summary;
        this.cover = cover;
        this.sku = sku;
        this.description = description;
        this.category_id = category_id;
        this.created_at = created_at;
        this.deleted_at = deleted_at;
        this.img_product = img_product;
    }

    @Override
    public String toString() {
        return String.format("Product[id=%d]", id);
    }

    public Product(Long productId, String nameProduct, long priceProduct) {
        this.id = productId;
        this.nameProduct = nameProduct;
        this.priceProduct = priceProduct;
    }
}
