package com.inhabas.api.domain.signUp.repository;

import com.inhabas.api.auth.domain.oauth2.member.domain.entity.Member;
import com.inhabas.api.auth.domain.oauth2.member.repository.MemberRepository;
import com.inhabas.api.auth.domain.oauth2.socialAccount.type.UID;
import com.inhabas.api.domain.member.domain.entity.MemberTest;
import com.inhabas.api.domain.questionnaire.domain.Questionnaire;
import com.inhabas.api.domain.questionnaire.repository.QuestionnaireRepository;
import com.inhabas.api.domain.signUp.domain.entity.Answer;
import com.inhabas.testAnnotataion.DefaultDataJpaTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.inhabas.api.auth.domain.oauth2.OAuth2Provider.GOOGLE;
import static org.assertj.core.api.Assertions.assertThat;


@DefaultDataJpaTest
class AnswerRepositoryTest {

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private MemberRepository memberRepository;


    @Test
    @Transactional
    @DisplayName("memberId로 작성한 답변을 가져올 수 있다.")
    void findByMember_Id() {
        //given
        Member member = MemberTest.signingUpMember1();
        memberRepository.save(member);
        Questionnaire questionnaire = new Questionnaire(1L, "hello");
        questionnaireRepository.save(questionnaire);
        String content = "Ok... bye";
        Answer answer = new Answer(member, questionnaire, content);

        //when
        Member resultMember  = memberRepository.findByProviderAndUid(GOOGLE, new UID("1249846925629348")).orElseThrow();
        answerRepository.save(answer);
        List<Answer> answers = answerRepository.findByMember_Id(resultMember.getId());

        //then
        assertThat(answers).contains(answer);

    }
}