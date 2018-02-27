package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.OrganazerApp;

import com.mycompany.myapp.domain.Task;
import com.mycompany.myapp.repository.TaskRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.domain.enumeration.Status;
import com.mycompany.myapp.domain.enumeration.Repeat;
/**
 * Test class for the TaskResource REST controller.
 *
 * @see TaskResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrganazerApp.class)
public class TaskResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DISCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DISCRIPTION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_START = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_START = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATE_END = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_END = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_PRIORITET = 1L;
    private static final Long UPDATED_PRIORITET = 2L;

    private static final Double DEFAULT_TIME = 1D;
    private static final Double UPDATED_TIME = 2D;

    private static final Double DEFAULT_PROGRES = 1D;
    private static final Double UPDATED_PROGRES = 2D;

    private static final Status DEFAULT_STATUS = Status.INPROGRESS;
    private static final Status UPDATED_STATUS = Status.OPEN;

    private static final Boolean DEFAULT_IMPOTMENT = false;
    private static final Boolean UPDATED_IMPOTMENT = true;

    private static final Boolean DEFAULT_QUICK = false;
    private static final Boolean UPDATED_QUICK = true;

    private static final Repeat DEFAULT_REPEAT = Repeat.NOREPEAT;
    private static final Repeat UPDATED_REPEAT = Repeat.DAYLY;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTaskMockMvc;

    private Task task;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TaskResource taskResource = new TaskResource(taskRepository);
        this.restTaskMockMvc = MockMvcBuilders.standaloneSetup(taskResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Task createEntity(EntityManager em) {
        Task task = new Task()
            .name(DEFAULT_NAME)
            .discription(DEFAULT_DISCRIPTION)
            .dateStart(DEFAULT_DATE_START)
            .dateEnd(DEFAULT_DATE_END)
            .prioritet(DEFAULT_PRIORITET)
            .time(DEFAULT_TIME)
            .progres(DEFAULT_PROGRES)
            .status(DEFAULT_STATUS)
            .impotment(DEFAULT_IMPOTMENT)
            .quick(DEFAULT_QUICK)
            .repeat(DEFAULT_REPEAT);
        return task;
    }

    @Before
    public void initTest() {
        task = createEntity(em);
    }

    @Test
    @Transactional
    public void createTask() throws Exception {
        int databaseSizeBeforeCreate = taskRepository.findAll().size();

        // Create the Task
        restTaskMockMvc.perform(post("/api/tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(task)))
            .andExpect(status().isCreated());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeCreate + 1);
        Task testTask = taskList.get(taskList.size() - 1);
        assertThat(testTask.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTask.getDiscription()).isEqualTo(DEFAULT_DISCRIPTION);
        assertThat(testTask.getDateStart()).isEqualTo(DEFAULT_DATE_START);
        assertThat(testTask.getDateEnd()).isEqualTo(DEFAULT_DATE_END);
        assertThat(testTask.getPrioritet()).isEqualTo(DEFAULT_PRIORITET);
        assertThat(testTask.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testTask.getProgres()).isEqualTo(DEFAULT_PROGRES);
        assertThat(testTask.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTask.isImpotment()).isEqualTo(DEFAULT_IMPOTMENT);
        assertThat(testTask.isQuick()).isEqualTo(DEFAULT_QUICK);
        assertThat(testTask.getRepeat()).isEqualTo(DEFAULT_REPEAT);
    }

    @Test
    @Transactional
    public void createTaskWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taskRepository.findAll().size();

        // Create the Task with an existing ID
        task.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskMockMvc.perform(post("/api/tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(task)))
            .andExpect(status().isBadRequest());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTasks() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList
        restTaskMockMvc.perform(get("/api/tasks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(task.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].discription").value(hasItem(DEFAULT_DISCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].dateStart").value(hasItem(sameInstant(DEFAULT_DATE_START))))
            .andExpect(jsonPath("$.[*].dateEnd").value(hasItem(sameInstant(DEFAULT_DATE_END))))
            .andExpect(jsonPath("$.[*].prioritet").value(hasItem(DEFAULT_PRIORITET.intValue())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME.doubleValue())))
            .andExpect(jsonPath("$.[*].progres").value(hasItem(DEFAULT_PROGRES.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].impotment").value(hasItem(DEFAULT_IMPOTMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].quick").value(hasItem(DEFAULT_QUICK.booleanValue())))
            .andExpect(jsonPath("$.[*].repeat").value(hasItem(DEFAULT_REPEAT.toString())));
    }

    @Test
    @Transactional
    public void getTask() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get the task
        restTaskMockMvc.perform(get("/api/tasks/{id}", task.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(task.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.discription").value(DEFAULT_DISCRIPTION.toString()))
            .andExpect(jsonPath("$.dateStart").value(sameInstant(DEFAULT_DATE_START)))
            .andExpect(jsonPath("$.dateEnd").value(sameInstant(DEFAULT_DATE_END)))
            .andExpect(jsonPath("$.prioritet").value(DEFAULT_PRIORITET.intValue()))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME.doubleValue()))
            .andExpect(jsonPath("$.progres").value(DEFAULT_PROGRES.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.impotment").value(DEFAULT_IMPOTMENT.booleanValue()))
            .andExpect(jsonPath("$.quick").value(DEFAULT_QUICK.booleanValue()))
            .andExpect(jsonPath("$.repeat").value(DEFAULT_REPEAT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTask() throws Exception {
        // Get the task
        restTaskMockMvc.perform(get("/api/tasks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTask() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);
        int databaseSizeBeforeUpdate = taskRepository.findAll().size();

        // Update the task
        Task updatedTask = taskRepository.findOne(task.getId());
        // Disconnect from session so that the updates on updatedTask are not directly saved in db
        em.detach(updatedTask);
        updatedTask
            .name(UPDATED_NAME)
            .discription(UPDATED_DISCRIPTION)
            .dateStart(UPDATED_DATE_START)
            .dateEnd(UPDATED_DATE_END)
            .prioritet(UPDATED_PRIORITET)
            .time(UPDATED_TIME)
            .progres(UPDATED_PROGRES)
            .status(UPDATED_STATUS)
            .impotment(UPDATED_IMPOTMENT)
            .quick(UPDATED_QUICK)
            .repeat(UPDATED_REPEAT);

        restTaskMockMvc.perform(put("/api/tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTask)))
            .andExpect(status().isOk());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeUpdate);
        Task testTask = taskList.get(taskList.size() - 1);
        assertThat(testTask.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTask.getDiscription()).isEqualTo(UPDATED_DISCRIPTION);
        assertThat(testTask.getDateStart()).isEqualTo(UPDATED_DATE_START);
        assertThat(testTask.getDateEnd()).isEqualTo(UPDATED_DATE_END);
        assertThat(testTask.getPrioritet()).isEqualTo(UPDATED_PRIORITET);
        assertThat(testTask.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testTask.getProgres()).isEqualTo(UPDATED_PROGRES);
        assertThat(testTask.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTask.isImpotment()).isEqualTo(UPDATED_IMPOTMENT);
        assertThat(testTask.isQuick()).isEqualTo(UPDATED_QUICK);
        assertThat(testTask.getRepeat()).isEqualTo(UPDATED_REPEAT);
    }

    @Test
    @Transactional
    public void updateNonExistingTask() throws Exception {
        int databaseSizeBeforeUpdate = taskRepository.findAll().size();

        // Create the Task

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTaskMockMvc.perform(put("/api/tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(task)))
            .andExpect(status().isCreated());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTask() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);
        int databaseSizeBeforeDelete = taskRepository.findAll().size();

        // Get the task
        restTaskMockMvc.perform(delete("/api/tasks/{id}", task.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Task.class);
        Task task1 = new Task();
        task1.setId(1L);
        Task task2 = new Task();
        task2.setId(task1.getId());
        assertThat(task1).isEqualTo(task2);
        task2.setId(2L);
        assertThat(task1).isNotEqualTo(task2);
        task1.setId(null);
        assertThat(task1).isNotEqualTo(task2);
    }
}
