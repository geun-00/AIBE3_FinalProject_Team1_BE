package com.back.domain.post.service;

import com.back.domain.post.dto.res.PostListResBody;
import com.back.domain.post.entity.Post;
import com.back.domain.post.repository.PostFavoriteRepository;
import com.back.domain.post.repository.PostRepository;
import com.back.domain.post.service.PostVectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostSearchService {

    private final PostVectorService postVectorService;
    private final PostRepository postRepository;
    private final PostFavoriteRepository postfavoriteRepository;

    public List<PostListResBody> searchPosts(String query, Long memberId) {

        List<Long> postIds = postVectorService.searchPostIds(query, 10);

        if (postIds.isEmpty()) return List.of();

        List<Post> posts = postIds.stream()
                .map(id -> postRepository.findById(id).orElse(null))
                .filter(Objects::nonNull)
                .toList();

        if (memberId != null) {

            return posts.stream()
                    .map(post -> {

                        boolean isFavorite = postfavoriteRepository.existsByMemberIdAndPostId(memberId, post.getId());

                        return PostListResBody.of(post, isFavorite);
                    })
                    .toList();
        }

        return posts.stream()
                .map(PostListResBody::of)
                .toList();
    }
}
