package org.zdxue.zk.console.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zdxue.zk.console.entity.Book;

/**
 * @author xuezhongde
 */
public interface BookRepository extends JpaRepository<Book, Integer> {
}
