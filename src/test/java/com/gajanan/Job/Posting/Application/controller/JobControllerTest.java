package com.gajanan.Job.Posting.Application.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gajanan.Job.Posting.Application.JobPostingApplication;
import com.gajanan.Job.Posting.Application.model.dto.RequestDTO;
import com.gajanan.Job.Posting.Application.model.entity.Job;
import com.gajanan.Job.Posting.Application.repository.JobRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(classes = JobPostingApplication.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class JobControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Job job;
    private RequestDTO requestDTO;

    @BeforeEach
    void setUp() {

        jobRepository.deleteAll();

        job = new Job();
        job.setTitle("Software Engineer");
        job.setCompany("Tech Corp");
        job.setDescription("Develop software");
        job.setLocation("New York");
        job.setSalary(60000.0);

        requestDTO = new RequestDTO();
        requestDTO.setTitle("Software Engineer");
        requestDTO.setCompany("Tech Corp");
        requestDTO.setDescription("Develop software");
        requestDTO.setLocation("New York");
        requestDTO.setSalary(60000.0);

    }

    @Test
    void createJob_ShouldReturnCreatedJob() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/jobs").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestDTO))).
                andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value(requestDTO.getTitle()));
    }

    @Test
    void getAllJobs_ShouldReturnListOfJobs() throws Exception {

        Job newJob = saveJob();

        mockMvc.perform(MockMvcRequestBuilders.get("/jobs").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].title").value(newJob.getTitle()));
    }



    @Test
    void getAllJobs_WhenListOfJobsIsEmpty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/jobs").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Currently, there are no job postings available. Please check back later."));
    }

    @Test
    void getJob_ShouldReturnJob_WhenJobExists() throws Exception {
        Job getJob=saveJob();
        mockMvc.perform(MockMvcRequestBuilders.get("/jobs/" + getJob.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value(getJob.getTitle()));
    }

    @Test
    void updateJob_ShouldReturnUpdatedJob() throws Exception {
        Job updateJob=saveJob();
        mockMvc.perform(MockMvcRequestBuilders.put("/jobs/"+ updateJob.getId()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value(requestDTO.getTitle()));
    }

    @Test
    void deleteJob() throws Exception {
        Job deleteJob=saveJob();
        mockMvc.perform(MockMvcRequestBuilders.delete("/jobs/" + deleteJob.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value(deleteJob.getTitle()));
    }

    private Job saveJob() {
        Job newJob = new Job();
        newJob.setTitle("Backend Developer");
        newJob.setCompany("Tech Solutions");
        newJob.setDescription("Develop backend systems");
        newJob.setLocation("San Francisco");
        newJob.setSalary(70000.0);
        newJob = jobRepository.save(newJob);
        return newJob;
    }
}