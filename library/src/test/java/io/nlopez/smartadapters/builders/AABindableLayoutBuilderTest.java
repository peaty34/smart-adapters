package io.nlopez.smartadapters.builders;

import android.view.View;
import android.view.ViewGroup;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RuntimeEnvironment;

import io.nlopez.smartadapters.mocks.MockLayout;
import io.nlopez.smartadapters.mocks.MockLayout2;
import io.nlopez.smartadapters.mocks.MockModel;
import io.nlopez.smartadapters.mocks.MockModel2;
import io.nlopez.smartadapters.utils.Mapper;
import io.nlopez.smartadapters.views.BindableFrameLayout;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Tests AndroidAnnotations style builder
 */
public class AABindableLayoutBuilderTest {

    @Mock
    MockLayout2 mockLayout2;

    MockModel2 mockModel2;
    MockModel mockModel;

    @Mock
    ViewGroup parent;

    private Mapper mapper;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mapper = new Mapper()
                .add(MockModel.class, MockLayout.class)
                .add(MockModel2.class, MockLayout2.class);

        mockModel = new MockModel();
        mockModel2 = new MockModel2();

        when(parent.getContext()).thenReturn(RuntimeEnvironment.application);
    }

    @Test
    public void test_build() {
        AABindableLayoutBuilder builder = new AABindableLayoutBuilder();
        int viewType = mapper.viewTypeFromViewClass(builder.viewType(mockModel2, 0, mapper));
        View bindableLayout = builder.build(parent, viewType, mockModel2, mapper);
        assertNotNull(bindableLayout);
        MockLayout2 mockLayout = (MockLayout2) bindableLayout;
        assertTrue(mockLayout.fromBuild);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_crash_if_not_aa() {
        AABindableLayoutBuilder builder = new AABindableLayoutBuilder();
        int viewType = mapper.viewTypeFromViewClass(builder.viewType(mockModel, 0, mapper));
        View bindableLayout = builder.build(parent, viewType, mockModel, mapper);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_crash_if_unknown_view_class() {
        AABindableLayoutBuilder builder = new AABindableLayoutBuilder();
        int viewType = mapper.viewTypeFromViewClass(builder.viewType(0, 0, mapper));
        View bindableLayout = builder.build(parent, 0, null, mapper);
    }
}
