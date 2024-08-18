# Website đánh giá nhà hàng: FoodX
Một trang web cho phép người dùng đánh giá và xếp hạng các địa điểm ăn uống, giúp họ đưa ra những lựa chọn sáng suốt về nhà hàng. Trang web có hệ thống xếp hạng, hiển thị các vị trí được xếp hạng hàng đầu và thịnh hành, từ đó hỗ trợ người dùng khi lựa chọn

## Authors

- [@DwngLee - Back-end](https://github.com/DwngLee)
- [@Semicof - Front-end](https://github.com/Semicof)

## API Reference

- API docs: https://foodxbe-production.up.railway.app/api/swagger-ui/index.html

## Deployment
## ***Server***


**Bước 1: Clone dự án ở repo**

```bash
  git clone https://github.com/DwngLe/FoodX_BE
```
**Bước 2: Setup Maven (có thể bỏ qua nếu đã cài đặt Maven)**
- Cài đặt maven: ` https://maven.apache.org/download.cgi`
- Giải nén file và lưu vào thư mục
- Set MAVEN_HOME vào `System variable`
![ibo5A](https://github.com/DwngLee/Project-IOT/assets/156188368/8aadced8-9f5c-4a09-9007-b2ddc2b909ff)
- Set path cho maven
![wl0eU](https://github.com/DwngLee/Project-IOT/assets/156188368/e9850992-7115-4b4e-a9cf-e297ab476fd3)
- Thêm maven plugin vào file POM.XML
```bash
   <build>
       <plugins>
         <plugin>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-maven-plugin</artifactId>
          </plugin>
        </plugins>
      </build>
```


**Bước 3: Build Spring Boot Project bằng Maven**
```bash
  mvn package
```
hoặc
```bash
  mvn install / mvn clean install
```
**Bước 4: Chạy ứng dụng Spring Boot sử dụng Maven**
```bash
  mvn spring-boot:run
```






