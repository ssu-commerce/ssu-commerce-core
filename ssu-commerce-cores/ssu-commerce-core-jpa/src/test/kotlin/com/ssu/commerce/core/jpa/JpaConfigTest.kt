package com.ssu.commerce.core.jpa

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class TestEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long?,
    var name: String
) : BaseEntity() {
    constructor(name: String) : this(null, name)
}

interface TestEntityRepository : JpaRepository<TestEntity, Long>

@SpringBootTest
@ActiveProfiles("test")
internal class JpaConfigTest @Autowired constructor(val testRepository: TestEntityRepository) {

    @Test
    @Transactional
    fun `BaseEntity 작동 테스트`() {
        // when
        val savedEntity = testRepository.save(TestEntity("test name"))

        // then
        testRepository.findById(savedEntity.id!!)
            .get()
            .let {
                assertThat(it.id).isNotNull
                assertThat(it).returns("test name", TestEntity::name)
                assertThat(it).returns("System", TestEntity::createdWho)
                assertThat(it).returns("System", TestEntity::updatedWho)
                assertThat(it.createdAt).isNotNull
                assertThat(it.updatedAt).isNotNull
            }
    }
}
