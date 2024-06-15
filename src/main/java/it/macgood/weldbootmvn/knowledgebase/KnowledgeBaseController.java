package it.macgood.weldbootmvn.knowledgebase;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("api/v1/knowledgebase")
public class KnowledgeBaseController {
    private final KnowledgeBaseRepository knowledgeBaseRepository;

    @GetMapping
    public ResponseEntity<?> getKnowledge() {
        return ResponseEntity.ok(knowledgeBaseRepository.findAll());
    }
}
