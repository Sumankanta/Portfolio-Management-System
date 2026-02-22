package com.suman.portfolio_backend.repository;

import com.suman.portfolio_backend.entity.User;
import com.suman.portfolio_backend.entity.Project;
import com.suman.portfolio_backend.entity.Skill;
import com.suman.portfolio_backend.entity.ChatMessage;
import com.suman.portfolio_backend.entity.Payment;
import com.suman.portfolio_backend.entity.enums.SenderType;
import com.suman.portfolio_backend.entity.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Repository interfaces
 * Uses in-memory H2 database for testing
 */
@DataJpaTest
@ActiveProfiles("test")
public class RepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    private User testUser;
    private Project testProject;
    private Skill testSkill;

    @BeforeEach
    void setUp() {
        // Clean up before each test
        userRepository.deleteAll();
        projectRepository.deleteAll();
        skillRepository.deleteAll();
        chatMessageRepository.deleteAll();
        paymentRepository.deleteAll();

        // Create test user
        testUser = User.builder()
                .username("testuser")
                .email("test@example.com")
                .passwordHash("hashedpassword")
                .fullName("Test User")
                .bio("Test bio")
                .build();
        testUser = userRepository.save(testUser);

        // Create test skill
        testSkill = Skill.builder()
                .name("Java")
                .category("Backend")
                .proficiencyLevel(85)
                .build();
        testSkill = skillRepository.save(testSkill);

        // Create test project
        testProject = Project.builder()
                .title("Test Project")
                .description("Test Description")
                .build();
        testProject = projectRepository.save(testProject);
    }

    // ========== UserRepository Tests ==========

    @Test
    void testSaveAndFindUser() {
        assertNotNull(testUser.getId());
        assertEquals("testuser", testUser.getUsername());
    }

    @Test
    void testFindByUsername() {
        Optional<User> found = userRepository.findByUsername("testuser");
        assertTrue(found.isPresent());
        assertEquals("test@example.com", found.get().getEmail());
    }

    @Test
    void testFindByEmail() {
        Optional<User> found = userRepository.findByEmail("test@example.com");
        assertTrue(found.isPresent());
        assertEquals("testuser", found.get().getUsername());
    }

    @Test
    void testExistsByUsername() {
        assertTrue(userRepository.existsByUsername("testuser"));
        assertFalse(userRepository.existsByUsername("nonexistent"));
    }

    @Test
    void testExistsByEmail() {
        assertTrue(userRepository.existsByEmail("test@example.com"));
        assertFalse(userRepository.existsByEmail("nonexistent@example.com"));
    }

    @Test
    void testFindByFullNameContaining() {
        List<User> users = userRepository.findByFullNameContainingIgnoreCase("test");
        assertFalse(users.isEmpty());
        assertEquals(1, users.size());
    }

    @Test
    void testSearchByUsernameOrEmail() {
        List<User> users = userRepository.searchByUsernameOrEmail("test");
        assertFalse(users.isEmpty());
    }

    // ========== ProjectRepository Tests ==========

    @Test
    void testSaveAndFindProject() {
        assertNotNull(testProject.getId());
        assertEquals("Test Project", testProject.getTitle());
    }

    @Test
    void testFindByTitleContaining() {
        List<Project> projects = projectRepository.findByTitleContainingIgnoreCase("test");
        assertFalse(projects.isEmpty());
        assertEquals(1, projects.size());
    }

    @Test
    void testFindTop10Recent() {
        // Create additional projects
        for (int i = 0; i < 5; i++) {
            Project p = Project.builder()
                    .title("Project " + i)
                    .description("Description " + i)
                    .build();
            projectRepository.save(p);
        }

        List<Project> recent = projectRepository.findTop10ByOrderByCreatedAtDesc();
        assertFalse(recent.isEmpty());
        assertTrue(recent.size() <= 10);
    }

    @Test
    void testSearchByTitleOrDescription() {
        List<Project> projects = projectRepository.searchByTitleOrDescription("test");
        assertFalse(projects.isEmpty());
    }

    @Test
    void testCountTotalProjects() {
        long count = projectRepository.countTotalProjects();
        assertEquals(1, count);
    }

    // ========== SkillRepository Tests ==========

    @Test
    void testSaveAndFindSkill() {
        assertNotNull(testSkill.getId());
        assertEquals("Java", testSkill.getName());
    }

    @Test
    void testFindByCategory() {
        List<Skill> skills = skillRepository.findByCategory("Backend");
        assertFalse(skills.isEmpty());
        assertEquals(1, skills.size());
    }

    @Test
    void testFindByCategoryIgnoreCase() {
        List<Skill> skills = skillRepository.findByCategoryIgnoreCase("backend");
        assertFalse(skills.isEmpty());
    }

    @Test
    void testFindByProficiencyLevel() {
        List<Skill> skills = skillRepository.findByProficiencyLevelGreaterThanEqual(80);
        assertFalse(skills.isEmpty());
        assertEquals(1, skills.size());
    }

    @Test
    void testFindByProficiencyLevelBetween() {
        List<Skill> skills = skillRepository.findByProficiencyLevelBetween(80, 90);
        assertFalse(skills.isEmpty());
    }

    @Test
    void testFindAllCategories() {
        List<String> categories = skillRepository.findAllCategories();
        assertFalse(categories.isEmpty());
        assertTrue(categories.contains("Backend"));
    }

    @Test
    void testSearchByNameOrCategory() {
        List<Skill> skills = skillRepository.searchByNameOrCategory("java");
        assertFalse(skills.isEmpty());
    }

    // ========== ChatMessageRepository Tests ==========

    @Test
    void testSaveAndFindChatMessage() {
        ChatMessage message = ChatMessage.builder()
                .sessionId("session-123")
                .messageContent("Hello, how can I help?")
                .senderType(SenderType.AI)
                .isRead(false)
                .build();

        ChatMessage saved = chatMessageRepository.save(message);
        assertNotNull(saved.getId());
    }

    @Test
    void testFindBySessionId() {
        ChatMessage message = ChatMessage.builder()
                .sessionId("session-123")
                .messageContent("Test message")
                .senderType(SenderType.USER)
                .build();
        chatMessageRepository.save(message);

        List<ChatMessage> messages = chatMessageRepository.findBySessionId("session-123");
        assertFalse(messages.isEmpty());
    }

    @Test
    void testFindBySenderType() {
        ChatMessage aiMessage = ChatMessage.builder()
                .sessionId("session-123")
                .messageContent("AI response")
                .senderType(SenderType.AI)
                .build();
        chatMessageRepository.save(aiMessage);

        List<ChatMessage> messages = chatMessageRepository.findBySenderType(SenderType.AI);
        assertFalse(messages.isEmpty());
    }

    @Test
    void testFindUnreadMessages() {
        ChatMessage unreadMessage = ChatMessage.builder()
                .sessionId("session-123")
                .messageContent("Unread message")
                .senderType(SenderType.ADMIN)
                .isRead(false)
                .build();
        chatMessageRepository.save(unreadMessage);

        List<ChatMessage> unread = chatMessageRepository.findByIsReadFalse();
        assertFalse(unread.isEmpty());
    }

    @Test
    void testCountUnreadMessages() {
        ChatMessage unreadMessage = ChatMessage.builder()
                .sessionId("session-123")
                .messageContent("Unread")
                .senderType(SenderType.USER)
                .isRead(false)
                .build();
        chatMessageRepository.save(unreadMessage);

        long count = chatMessageRepository.countUnreadMessages();
        assertEquals(1, count);
    }

    // ========== PaymentRepository Tests ==========

    @Test
    void testSaveAndFindPayment() {
        Payment payment = Payment.builder()
                .user(testUser)
                .amount(new BigDecimal("99.99"))
                .currency("USD")
                .status(PaymentStatus.SUCCESS)
                .build();

        Payment saved = paymentRepository.save(payment);
        assertNotNull(saved.getId());
    }

    @Test
    void testFindByUserId() {
        Payment payment = Payment.builder()
                .user(testUser)
                .amount(new BigDecimal("50.00"))
                .currency("USD")
                .status(PaymentStatus.PENDING)
                .build();
        paymentRepository.save(payment);

        List<Payment> payments = paymentRepository.findByUserId(testUser.getId());
        assertFalse(payments.isEmpty());
    }

    @Test
    void testFindByStatus() {
        Payment payment = Payment.builder()
                .user(testUser)
                .amount(new BigDecimal("100.00"))
                .currency("USD")
                .status(PaymentStatus.SUCCESS)
                .build();
        paymentRepository.save(payment);

        List<Payment> payments = paymentRepository.findByStatus(PaymentStatus.SUCCESS);
        assertFalse(payments.isEmpty());
    }

    @Test
    void testSumAmountByStatus() {
        Payment payment1 = Payment.builder()
                .user(testUser)
                .amount(new BigDecimal("50.00"))
                .currency("USD")
                .status(PaymentStatus.SUCCESS)
                .build();
        paymentRepository.save(payment1);

        Payment payment2 = Payment.builder()
                .user(testUser)
                .amount(new BigDecimal("75.00"))
                .currency("USD")
                .status(PaymentStatus.SUCCESS)
                .build();
        paymentRepository.save(payment2);

        BigDecimal total = paymentRepository.sumAmountByStatus(PaymentStatus.SUCCESS);
        assertEquals(new BigDecimal("125.00"), total);
    }

    @Test
    void testCountByStatus() {
        Payment payment = Payment.builder()
                .user(testUser)
                .amount(new BigDecimal("25.00"))
                .currency("USD")
                .status(PaymentStatus.FAILED)
                .build();
        paymentRepository.save(payment);

        long count = paymentRepository.countByStatus(PaymentStatus.FAILED);
        assertEquals(1, count);
    }

    @Test
    void testFindByTransactionId() {
        Payment payment = Payment.builder()
                .user(testUser)
                .amount(new BigDecimal("150.00"))
                .currency("USD")
                .status(PaymentStatus.SUCCESS)
                .transactionId("TXN-12345")
                .build();
        paymentRepository.save(payment);

        Optional<Payment> found = paymentRepository.findByTransactionId("TXN-12345");
        assertTrue(found.isPresent());
        assertEquals(new BigDecimal("150.00"), found.get().getAmount());
    }
}