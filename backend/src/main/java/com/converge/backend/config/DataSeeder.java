package com.converge.backend.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.converge.backend.entity.Experience;
import com.converge.backend.entity.Schedule;
import com.converge.backend.repository.ExperienceRepository;
import com.converge.backend.repository.ScheduleRepository;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedData(
            ExperienceRepository experienceRepository,
            ScheduleRepository scheduleRepository
    ) {

        return args -> {

            if (experienceRepository.count() == 0) {

                List<Experience> experiences = List.of(

                        create(
                                "Midnight Protocol",
                                "Movie",
                                "Action",
                                "English",
                                "Chennai",
                                "Nova Cinemas",
                                142,
                                "249.00",
                                "A cyber-thriller following an elite team racing against a city-wide digital blackout.",
                                "https://images.unsplash.com/photo-1489599849927-2ee91cede3ba"
                        ),

                        create(
                                "Echoes of Tomorrow",
                                "Movie",
                                "Sci-Fi",
                                "English",
                                "Chennai",
                                "Orbit Screens",
                                128,
                                "299.00",
                                "A futuristic journey where a young engineer discovers messages arriving from twenty years ahead.",
                                "https://images.unsplash.com/photo-1440404653325-ab127d49abc1"
                        ),

                        create(
                                "Monsoon Letters",
                                "Movie",
                                "Drama",
                                "Tamil",
                                "Chennai",
                                "CineVerse",
                                118,
                                "199.00",
                                "Two strangers reconnect through a collection of letters during one unforgettable monsoon.",
                                "https://images.unsplash.com/photo-1485846234645-a62644f84728"
                        ),

                        create(
                                "Neon District",
                                "Movie",
                                "Crime",
                                "Tamil",
                                "Chennai",
                                "Nova Cinemas",
                                135,
                                "229.00",
                                "A detective enters Chennai's underground nightlife to uncover a missing-person mystery.",
                                "https://images.unsplash.com/photo-1517604931442-7e0c8ed2963c"
                        ),

                        create(
                                "The Last Signal",
                                "Movie",
                                "Thriller",
                                "Hindi",
                                "Chennai",
                                "Orbit Screens",
                                121,
                                "219.00",
                                "A radio operator receives a distress signal that should not exist.",
                                "https://images.unsplash.com/photo-1516280440614-37939bbacd81"
                        ),

                        create(
                                "Paper Planets",
                                "Movie",
                                "Animation",
                                "English",
                                "Bengaluru",
                                "Skyline Multiplex",
                                104,
                                "189.00",
                                "A visually rich animated adventure about two siblings building their own imaginary universe.",
                                "https://images.unsplash.com/photo-1535016120720-40c646be5580"
                        ),

                        create(
                                "Laugh Track Live",
                                "Event",
                                "Comedy",
                                "English",
                                "Chennai",
                                "The Grand Arena",
                                110,
                                "799.00",
                                "A live comedy evening featuring emerging performers and crowd-driven improvisation.",
                                "https://images.unsplash.com/photo-1524368535928-5b5e00ddc76b"
                        ),

                        create(
                                "Indie Nights",
                                "Event",
                                "Music",
                                "English",
                                "Chennai",
                                "Harbour Stage",
                                150,
                                "999.00",
                                "An intimate live music experience featuring independent artists from across India.",
                                "https://images.unsplash.com/photo-1501386761578-eac5c94b800a"
                        ),

                        create(
                                "Chennai Food Stories",
                                "Event",
                                "Food",
                                "Tamil",
                                "Chennai",
                                "Coastal Convention Hall",
                                180,
                                "599.00",
                                "A celebration of regional food, chefs, street flavours and culinary storytelling.",
                                "https://images.unsplash.com/photo-1555939594-58d7cb561ad1"
                        ),

                        create(
                                "Future Builders",
                                "Event",
                                "Technology",
                                "English",
                                "Bengaluru",
                                "TechPark Convention Centre",
                                240,
                                "1299.00",
                                "A technology showcase featuring startups, developers and emerging digital products.",
                                "https://images.unsplash.com/photo-1540575467063-178a50c2df87"
                        ),

                        create(
                                "Canvas After Dark",
                                "Event",
                                "Art",
                                "English",
                                "Mumbai",
                                "Studio 27",
                                120,
                                "699.00",
                                "An immersive late-evening exhibition combining visual art, music and interactive installations.",
                                "https://images.unsplash.com/photo-1561214115-f2f134cc4912"
                        ),

                        create(
                                "Champions Night",
                                "Sports",
                                "Cricket",
                                "English",
                                "Chennai",
                                "Metro Sports Arena",
                                210,
                                "1499.00",
                                "A high-energy evening featuring a professional cricket exhibition match.",
                                "https://images.unsplash.com/photo-1531415074968-036ba1b575da"
                        ),

                        create(
                                "City Hoops",
                                "Sports",
                                "Basketball",
                                "English",
                                "Bengaluru",
                                "Urban Basketball Arena",
                                150,
                                "899.00",
                                "Watch top local basketball talent compete in a fast-paced championship fixture.",
                                "https://images.unsplash.com/photo-1546519638-68e109498ffc"
                        ),

                        create(
                                "Rally Rush",
                                "Sports",
                                "Motorsport",
                                "English",
                                "Chennai",
                                "Coastal Racing Circuit",
                                240,
                                "1799.00",
                                "A weekend motorsport experience featuring high-speed racing and live paddock access.",
                                "https://images.unsplash.com/photo-1503736334956-4c8f8e92946d"
                        ),

                        create(
                                "Smash Arena",
                                "Sports",
                                "Badminton",
                                "English",
                                "Hyderabad",
                                "Velocity Arena",
                                160,
                                "749.00",
                                "A professional badminton tournament featuring intense singles and doubles matches.",
                                "https://images.unsplash.com/photo-1626224583764-f87db24ac4ea"
                        ),

                        create(
                                "Run Chennai",
                                "Sports",
                                "Marathon",
                                "English",
                                "Chennai",
                                "Marina Sports Grounds",
                                180,
                                "499.00",
                                "A city-wide running experience designed for beginners, enthusiasts and competitive runners.",
                                "https://images.unsplash.com/photo-1552674605-db6ffd4facb5"
                        ),

                        create(
                                "The Glass Room",
                                "Theatre",
                                "Drama",
                                "English",
                                "Chennai",
                                "Black Box Theatre",
                                125,
                                "549.00",
                                "A psychological stage drama performed in an intimate black-box theatre.",
                                "https://images.unsplash.com/photo-1503095396549-807759245b35"
                        ),

                        create(
                                "The Silent Guest",
                                "Theatre",
                                "Mystery",
                                "English",
                                "Mumbai",
                                "Grand Stage",
                                135,
                                "699.00",
                                "A mysterious guest arrives at a dinner party and changes everyone's story.",
                                "https://images.unsplash.com/photo-1507924538820-ede94a04019d"
                        ),

                        create(
                                "Kaatrin Kural",
                                "Theatre",
                                "Drama",
                                "Tamil",
                                "Chennai",
                                "Kala Mandapam",
                                130,
                                "449.00",
                                "A Tamil stage production exploring family, memory and the changing identity of a coastal town.",
                                "https://images.unsplash.com/photo-1507676184212-d03ab07a01bf"
                        ),

                        create(
                                "The Final Act",
                                "Theatre",
                                "Thriller",
                                "English",
                                "Bengaluru",
                                "Spotlight Theatre",
                                115,
                                "599.00",
                                "A theatre director discovers that the final scene of his play is becoming reality.",
                                "https://images.unsplash.com/photo-1503095396549-807759245b35"
                        ),

                        create(
                                "Dreamscape",
                                "Event",
                                "Immersive",
                                "English",
                                "Mumbai",
                                "Immersive Lab",
                                95,
                                "899.00",
                                "An interactive experience combining light, sound and storytelling across multiple rooms.",
                                "https://images.unsplash.com/photo-1514525253161-7a46d19cd819"
                        ),

                        create(
                                "Retro Rewind",
                                "Event",
                                "Music",
                                "Tamil",
                                "Chennai",
                                "Harbour Stage",
                                165,
                                "899.00",
                                "A nostalgic live music evening celebrating classic Tamil and Indian sounds.",
                                "https://images.unsplash.com/photo-1492684223066-81342ee5ff30"
                        ),

                        create(
                                "Ocean Planet",
                                "Movie",
                                "Documentary",
                                "English",
                                "Mumbai",
                                "CineVerse",
                                96,
                                "179.00",
                                "A cinematic documentary exploring the hidden ecosystems beneath the world's oceans.",
                                "https://images.unsplash.com/photo-1469474968028-56623f02e42e"
                        ),

                        create(
                                "Code Runner",
                                "Movie",
                                "Action",
                                "Hindi",
                                "Bengaluru",
                                "Skyline Multiplex",
                                130,
                                "249.00",
                                "A software developer is pulled into a dangerous race involving stolen technology.",
                                "https://images.unsplash.com/photo-1489599849927-2ee91cede3ba"
                        ),

                        create(
                                "Beyond The Line",
                                "Sports",
                                "Football",
                                "English",
                                "Mumbai",
                                "City Football Arena",
                                180,
                                "999.00",
                                "An exciting professional football fixture featuring two competitive city teams.",
                                "https://images.unsplash.com/photo-1579952363873-27f3bade9f55"
                        ),

                        create(
                                "Rhythm Republic",
                                "Event",
                                "Dance",
                                "English",
                                "Bengaluru",
                                "Pulse Convention Hall",
                                140,
                                "799.00",
                                "A high-energy dance showcase bringing together contemporary and street performers.",
                                "https://images.unsplash.com/photo-1504609813442-a8924e83f76e"
                        ),

                        create(
                                "A Room of Stories",
                                "Theatre",
                                "Drama",
                                "Tamil",
                                "Madurai",
                                "Heritage Theatre",
                                110,
                                "399.00",
                                "A collection of interconnected stories performed through minimal staging and powerful dialogue.",
                                "https://images.unsplash.com/photo-1507676184212-d03ab07a01bf"
                        ),

                        create(
                                "Starlight Symphony",
                                "Event",
                                "Classical",
                                "English",
                                "Chennai",
                                "Music Hall One",
                                130,
                                "1199.00",
                                "An orchestral evening featuring cinematic themes and contemporary arrangements.",
                                "https://images.unsplash.com/photo-1465847899084-d164df4dedc6"
                        ),

                        create(
                                "Wild India",
                                "Movie",
                                "Documentary",
                                "English",
                                "Chennai",
                                "Orbit Screens",
                                108,
                                "199.00",
                                "A nature documentary exploring India's forests, wildlife and changing landscapes.",
                                "https://images.unsplash.com/photo-1516026672322-bc52d61a55d5"
                        ),

                        create(
                                "Urban Legends",
                                "Theatre",
                                "Mystery",
                                "Tamil",
                                "Chennai",
                                "Black Box Theatre",
                                120,
                                "499.00",
                                "Four strangers share stories of unexplained events that slowly begin to connect.",
                                "https://images.unsplash.com/photo-1503095396549-807759245b35"
                        ),

                        create(
                                "Startup Arena",
                                "Event",
                                "Business",
                                "English",
                                "Bengaluru",
                                "Innovation Centre",
                                200,
                                "999.00",
                                "Founders, builders and investors come together for a day of ideas and product showcases.",
                                "https://images.unsplash.com/photo-1556761175-b413da4baf72"
                        )
                );

                experienceRepository.saveAll(experiences);
            }

            List<Experience> experiences =
                    experienceRepository.findAll();

            LocalDate today = LocalDate.now();

            for (Experience experience : experiences) {
                ensureUpcomingSchedules(
                        scheduleRepository,
                        experience,
                        today
                );
            }
        };
    }

    private void ensureUpcomingSchedules(
            ScheduleRepository scheduleRepository,
            Experience experience,
            LocalDate today
    ) {

        int[] movieOffsets = {1, 2, 3, 4, 7};
        int[] otherOffsets = {1, 2, 3, 7};

        int[] offsets = experience.getCategory().equalsIgnoreCase("Movie")
                ? movieOffsets
                : otherOffsets;

        for (int offset : offsets) {
            LocalDate date = today.plusDays(offset);

            List<Schedule> existing =
                    scheduleRepository
                            .findByExperienceIdAndScheduleDateAndActiveTrue(
                                    experience.getId(),
                                    date
                            );

            if (!existing.isEmpty()) {
                continue;
            }

            if (experience.getCategory().equalsIgnoreCase("Movie")) {
                createSchedule(
                        scheduleRepository,
                        experience,
                        date,
                        LocalTime.of(10, 0),
                        LocalTime.of(10, 0).plusMinutes(
                                experience.getDurationMinutes()
                        ),
                        experience.getStartingPrice(),
                        120
                );
                createSchedule(
                        scheduleRepository,
                        experience,
                        date,
                        LocalTime.of(13, 30),
                        LocalTime.of(13, 30).plusMinutes(
                                experience.getDurationMinutes()
                        ),
                        experience.getStartingPrice(),
                        120
                );
                createSchedule(
                        scheduleRepository,
                        experience,
                        date,
                        LocalTime.of(18, 0),
                        LocalTime.of(18, 0).plusMinutes(
                                experience.getDurationMinutes()
                        ),
                        experience.getStartingPrice(),
                        120
                );
            } else {
                createSchedule(
                        scheduleRepository,
                        experience,
                        date,
                        LocalTime.of(11, 0),
                        LocalTime.of(11, 0).plusMinutes(
                                experience.getDurationMinutes()
                        ),
                        experience.getStartingPrice(),
                        200
                );
                createSchedule(
                        scheduleRepository,
                        experience,
                        date,
                        LocalTime.of(16, 0),
                        LocalTime.of(16, 0).plusMinutes(
                                experience.getDurationMinutes()
                        ),
                        experience.getStartingPrice(),
                        200
                );
                createSchedule(
                        scheduleRepository,
                        experience,
                        date,
                        LocalTime.of(19, 0),
                        LocalTime.of(19, 0).plusMinutes(
                                experience.getDurationMinutes()
                        ),
                        experience.getStartingPrice(),
                        200
                );
            }
        }
    }

    private Experience create(
            String title,
            String category,
            String genre,
            String language,
            String city,
            String venueName,
            int durationMinutes,
            String startingPrice,
            String description,
            String imageUrl
    ) {

        Experience experience = new Experience();

        experience.setTitle(title);
        experience.setCategory(category);
        experience.setGenre(genre);
        experience.setLanguage(language);
        experience.setCity(city);
        experience.setVenueName(venueName);
        experience.setDurationMinutes(durationMinutes);
        experience.setStartingPrice(
                new BigDecimal(startingPrice)
        );
        experience.setDescription(description);
        experience.setImageUrl(imageUrl);
        experience.setActive(true);

        return experience;
    }

    private void createSchedule(
            ScheduleRepository scheduleRepository,
            Experience experience,
            LocalDate date,
            LocalTime startTime,
            LocalTime endTime,
            BigDecimal price,
            int totalSeats
    ) {

        Schedule schedule = new Schedule();

        schedule.setExperience(experience);
        schedule.setScheduleDate(date);
        schedule.setStartTime(startTime);
        schedule.setEndTime(endTime);
        schedule.setPrice(price);
        schedule.setTotalSeats(totalSeats);
        schedule.setAvailableSeats(totalSeats);
        schedule.setActive(true);

        scheduleRepository.save(schedule);
    }
}