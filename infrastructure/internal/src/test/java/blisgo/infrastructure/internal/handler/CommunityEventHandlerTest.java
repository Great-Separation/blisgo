package blisgo.infrastructure.internal.handler;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import blisgo.domain.community.Post;
import blisgo.domain.community.event.PostAddEvent;
import blisgo.domain.community.event.PostRemoveEvent;
import blisgo.domain.community.event.PostUpdateEvent;
import blisgo.domain.community.event.PostViewEvent;
import blisgo.infrastructure.internal.fixture.FixtureFactory;
import blisgo.infrastructure.internal.handler.base.EventsConfig;
import blisgo.usecase.port.domain.PostInputPort;
import blisgo.usecase.port.domain.PostOutputPort;
import blisgo.usecase.request.post.AddPost;
import blisgo.usecase.request.post.GetPost;
import blisgo.usecase.request.post.RemovePost;
import blisgo.usecase.request.post.UpdatePost;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import java.util.random.RandomGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

@ContextConfiguration(classes = EventsConfig.class)
@SpringBootTest(classes = PostInputPort.class)
@RecordApplicationEvents
class CommunityEventHandlerTest {

    final int REPEAT = 10;

    @MockBean
    PostOutputPort postOutputPort;

    @Autowired
    ApplicationEvents events;

    @InjectMocks
    PostInputPort postInputPort;

    FixtureMonkey fixtureMonkey = FixtureFactory.create(ConstructorPropertiesArbitraryIntrospector.INSTANCE);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        postInputPort = new PostInputPort(postOutputPort);
    }

    @RepeatedTest(REPEAT)
    @DisplayName("게시글 조회 이벤트 핸들링 테스트")
    void handlePostViewedEvent() {
        given(postOutputPort.read(anyLong())).willReturn(any(Post.class));
        var command = fixtureMonkey
                .giveMeBuilder(GetPost.class)
                .set("postId", Math.abs(RandomGenerator.getDefault().nextLong()))
                .set("pageable", Pageable.ofSize(10))
                .build()
                .sample();

        postInputPort.getPost(command);

        then(postOutputPort).should(times(1)).read(anyLong());
        var actual = events.stream(PostViewEvent.class).findAny().isPresent();
        assertTrue(actual);
    }

    @RepeatedTest(REPEAT)
    @DisplayName("게시글 추가 이벤트 핸들링 테스트")
    void handlePostAddEvent() {
        given(postOutputPort.create(any(Post.class))).willReturn(anyBoolean());
        var command = fixtureMonkey.giveMeOne(AddPost.class);

        postInputPort.addPost(command);

        then(postOutputPort).should(times(1)).create(any(Post.class));
        var actual = events.stream(PostAddEvent.class).findAny().isPresent();
        assertTrue(actual);
    }

    @RepeatedTest(REPEAT)
    @DisplayName("게시글 수정 이벤트 핸들링 테스트")
    void handlePostUpdateEvent() {
        given(postOutputPort.update(any(Post.class))).willReturn(anyBoolean());
        var command = fixtureMonkey.giveMeOne(UpdatePost.class);

        postInputPort.updatePost(command);

        then(postOutputPort).should(times(1)).update(any(Post.class));
        var actual = events.stream(PostUpdateEvent.class).findAny().isPresent();
        assertTrue(actual);
    }

    @RepeatedTest(REPEAT)
    @DisplayName("게시글 삭제 이벤트 핸들링 테스트")
    void handlePostRemoveEvent() {
        given(postOutputPort.delete(anyLong())).willReturn(anyBoolean());
        var command = fixtureMonkey.giveMeOne(RemovePost.class);

        postInputPort.removePost(command);

        then(postOutputPort).should(times(1)).delete(anyLong());
        var actual = events.stream(PostRemoveEvent.class).findAny().isPresent();
        assertTrue(actual);
    }
}
