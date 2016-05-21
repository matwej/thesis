package sk.fei.stuba.xpivarcim.db.entities;


import sk.fei.stuba.xpivarcim.db.entities.assignment.AssignmentFile;
import sk.fei.stuba.xpivarcim.db.entities.assignment.SourceFile;
import sk.fei.stuba.xpivarcim.db.entities.assignment.TestFile;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Assignment {

    @Id
    @Column(nullable = false)
    private long id;
    @Column
    private String codeLanguage;
    @Column
    private Date lastUpdated;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "assignment")
    private Set<SourceFile> sourceFiles;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "assignment")
    private Set<TestFile> testFiles;
    @Column
    private boolean saTest;
    @Transient
    private int status;

    public Assignment() {}

    public Assignment(long id, String codeLanguage, Date lastUpdated, Set<SourceFile> sourceFiles, Set<TestFile> testFiles, int status, boolean saTest) {
        this.id = id;
        this.codeLanguage = codeLanguage;
        this.lastUpdated = lastUpdated;
        this.sourceFiles = sourceFiles;
        this.testFiles = testFiles;
        this.status = status;
        this.saTest = saTest;
    }

    public void setFiles() {
        if (!(sourceFiles == null))
            for (AssignmentFile f : sourceFiles) {
                f.setAssignment(this);
            }
        if (!(testFiles == null))
        for (AssignmentFile f : testFiles) {
            f.setAssignment(this);
        }
    }

    public Set<TestFile> runTestFiles() {
        Set<TestFile> output = new HashSet<>();
        for (TestFile f : testFiles)
            if (f.isRunTest()) output.add(f);
        return output;
    }

    public Set<TestFile> unitTestFiles() {
        Set<TestFile> output = new HashSet<>();
        for (TestFile f : testFiles)
            if (!f.isRunTest()) output.add(f);
        return output;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public long getId() {
        return id;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public String getCodeLanguage() {
        return codeLanguage;
    }

    public Set<SourceFile> getSourceFiles() {
        return sourceFiles;
    }

    public Set<TestFile> getTestFiles() {
        return testFiles;
    }

    public int getStatus() {
        return status;
    }

    public boolean isSaTest() {
        return saTest;
    }
}
