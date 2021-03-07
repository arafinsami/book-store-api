package com.sami.service;

import static com.sami.utils.StringUtils.trim;
import static com.sami.utils.DateUtils.asDate;
import static com.sami.utils.StringUtils.isNotEmpty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Service;

import com.sami.entity.Book;
import com.sami.utils.PaginationParameters;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookFilterservice {

	private final EntityManager em;

	public Map<String, Object> getData(String title, Integer page, Integer size) {

		Map<String, Object> map = new HashMap<>();

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<Book> query = criteriaBuilder.createQuery(Book.class);

		CriteriaQuery<Long> cq = criteriaBuilder.createQuery(Long.class);

		Root<Book> root = query.from(Book.class);

		List<Predicate> predicates = new ArrayList<Predicate>();

		List<Predicate> countPredicates = new ArrayList<Predicate>();

		//predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("publicationDate"), asDate(LocalDate.now())));

		//countPredicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("publicationDate"), asDate(LocalDate.now())));

		if (isNotEmpty(title)) {
			predicates.add(criteriaBuilder.like(root.get("title"), "%" + trim(title) + "%"));
			countPredicates.add(criteriaBuilder.like(root.get("title"), "%" + trim(title) + "%"));
		}

		query.select(root).where(predicates.toArray(new Predicate[] {}));

		cq.select(criteriaBuilder.count(cq.from(Book.class))).where(countPredicates.toArray(new Predicate[] {}));

		query.orderBy(criteriaBuilder.desc(root.get("id")));

		TypedQuery<Book> typedQuery = em.createQuery(query).setFirstResult(size * page).setMaxResults(size);

		Query countQuery = em.createQuery(cq);

		Long total = (Long) countQuery.getSingleResult();

		List<Book> aList = typedQuery.getResultList();

		PaginationParameters.getdata(map, page, total, size, aList);

		return map;
	}
}
