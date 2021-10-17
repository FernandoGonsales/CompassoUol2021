package br.com.uolcompasso.productapi.repository;

import br.com.uolcompasso.productapi.entity.Product;
import br.com.uolcompasso.productapi.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;

@Repository
public class ProductCustomRepository {

    @Autowired
    private EntityManager entityManager;

    public List<Product> findByFilter(BigDecimal minPrice, BigDecimal maxPrice, String q) {
        String query = "SELECT p.* FROM product AS p ";
        String parameter = "WHERE ";

        if (minPrice != null) {
            query += parameter + "p.price >= :minPrice ";
            parameter = "AND ";
        }

        if (maxPrice != null) {
            query += parameter + "p.price <= :maxPrice ";
            parameter = "AND ";
        }

        if (q != null) {
            query += parameter + "(LOWER(p.name) LIKE :q OR LOWER(p.description) LIKE :q)";
        }

        Query buildQuery = entityManager.createNativeQuery(query, Product.class);

        if (minPrice != null) {
            buildQuery.setParameter("minPrice", minPrice);
        }

        if (maxPrice != null) {
            buildQuery.setParameter("maxPrice", maxPrice);
        }

        if (q != null) {
            buildQuery.setParameter("q", Utils.transformToSqlConsult(q));
        }

        return buildQuery.getResultList();
    }

}
