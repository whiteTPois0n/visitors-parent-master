package ch.elca.visitors.service.impl;

import ch.elca.visitors.persistence.entity.Visitor;
import ch.elca.visitors.persistence.enumeration.Title;
import ch.elca.visitors.persistence.repository.VisitorRepository;
import ch.elca.visitors.service.dto.VisitorDto;
import ch.elca.visitors.service.exception.ResourceNotFoundException;
import ch.elca.visitors.service.mapper.VisitorMapper;
import ch.elca.visitors.service.service.impl.VisitorServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class VisitorServiceImplTest {

    @InjectMocks
    private VisitorServiceImpl visitorService;

    @Mock
    private VisitorRepository visitorRepository;

    @Mock
    private VisitorMapper visitorMapper;

    private Visitor visitor;
    private VisitorDto visitorDto;


    @Before
    public void setUp() {
        visitor = new Visitor(1L, Title.Mr, "Chittesh", "Desmuk", "chittes_hm@hotmail.com", 57678220, "Vacoas", "Elca");
        visitorDto = new VisitorDto(1L, Title.Mr, "Chittesh", "Desmuk", "chittes_hm@hotmail.com", 57678220, "Vacoas", "Elca");
    }

    @Test
    public void testFindVisitor() {
        when(visitorRepository.findById(1L)).thenReturn(Optional.of(visitor));
        when(visitorMapper.mapToVisitorDto(visitor)).thenReturn(visitorDto);
        Assert.assertNotNull(visitorService.findVisitor(1L));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testFindVisitorException() {
        when(visitorRepository.findById(1L)).thenReturn(Optional.empty());
        Assert.assertNotNull(visitorService.findVisitor(1L));

    }

    @Test
    public void testAll() {

//        when(visitorRepository.findAll(PageRequest.of(1,1))).thenReturn(skillDomainPage);
//        when(skillDomainMapper.skillDomainToDto(skillDomain)).thenReturn(skillDomainDto);
//        Assert.assertNotNull(skillDomainService.getAllSkillDomain(PageRequest.of(1,1)));
    }


}
