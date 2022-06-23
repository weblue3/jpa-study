package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {

            tx.begin();

            //-- JPQL
            List<Member> resultList = em.createQuery("select m from Member as m", Member.class)
                .setFirstResult(1)
                .setMaxResults(10)
                .getResultList();

            resultList.stream().forEach(m -> {
                System.out.println("id : " + m.getId());
                System.out.println("name : " + m.getName());
            });

            tx.commit();

        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }

    //-- 등록
    public static void insertMember(Member member, EntityManager em){
        em.persist(member);
    }

    //-- 조회
    public static Member getMember(Long id, EntityManager em){
        return em.find(Member.class, id);
    }
}
