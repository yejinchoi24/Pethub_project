package pack.batch;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import pack.entity.Hospital;

//@Configuration
//@EnableBatchProcessing
public class BatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final DataSource dataSource;

    @Autowired
    public BatchConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager, DataSource dataSource) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.dataSource = dataSource;
    }

    // 1. CSV 파일에서 데이터를 읽어오는 ItemReader 설정
    @Bean
    public FlatFileItemReader<Hospital> reader() {
        FlatFileItemReader<Hospital> reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource("C:\\Users\\LSM\\Downloads\\위도경도다있는동물병원데이터3.csv"));
        reader.setLinesToSkip(1); // 첫 줄은 헤더이므로 스킵

        DefaultLineMapper<Hospital> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();

        // CSV 컬럼과 Hospital 엔티티 필드 매핑
        tokenizer.setNames("번호", "소재지전화", "소재지전체주소", "Latitude", "Longitude", "사업장명");

        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSet -> {
            Hospital hospital = new Hospital();

            // 모든 데이터를 읽음 (조건은 Processor에서 처리)
            hospital.setHospitalName(fieldSet.readString("사업장명"));
            hospital.setAddress(fieldSet.readString("소재지전체주소"));
            hospital.setPhoneNumber(fieldSet.readString("소재지전화"));

            // 위도와 경도 값 처리
            String latString = fieldSet.readString("Latitude");
            String lngString = fieldSet.readString("Longitude");

            hospital.setLat(isNumeric(latString) ? Double.parseDouble(latString) : 0.0);
            hospital.setLng(isNumeric(lngString) ? Double.parseDouble(lngString) : 0.0);

            return hospital;
        });

        reader.setLineMapper(lineMapper);

        return reader;
    }

    // 2. Processor: 주소가 '서울특별시'로 시작하는 데이터만 필터링
    @Bean
    public ItemProcessor<Hospital, Hospital> processor() {
        return hospital -> {
            // 소재지전체주소가 '서울특별시'로 시작하지 않으면 null 반환
            if (hospital.getAddress() == null || !hospital.getAddress().startsWith("서울특별시")) {
                return null; // 해당 데이터는 건너뜀
            }
            return hospital; // 유효한 데이터만 반환
        };
    }

    // 3. DB에 데이터를 저장하는 ItemWriter 설정
    @Bean
    public JdbcBatchItemWriter<Hospital> writer() {
        return new JdbcBatchItemWriterBuilder<Hospital>()
                .dataSource(dataSource)
                .sql("INSERT INTO hospital (hospital_name, address, phone_number, operating_hours, lat, lng, image_path) " +
                     "VALUES (:hospitalName, :address, :phoneNumber, :operatingHours, :lat, :lng, :imagePath)")
                .beanMapped()
                .build();
    }

    // Step 설정: 데이터를 읽고 쓰는 단계를 정의
    @Bean
    public Step step1() {
        return new StepBuilder("step1", jobRepository)
                .<Hospital, Hospital>chunk(10, transactionManager)
                .reader(reader())
                .processor(processor()) // Processor 추가
                .writer(writer())
                .build();
    }

    // Job 설정: 배치 작업의 전체 흐름 정의
    @Bean
    public Job importHospitalJob() {
        return new JobBuilder("importHospitalJob", jobRepository)
                .start(step1())
                .build();
    }

    // 숫자 유효성 확인 메서드
    private boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}