package spring.custom.common.mybatis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;

@JsonFormat(shape = Shape.OBJECT)
public class PageResponse<T> implements List<T> {
  
  private List<T> pageData;
  private PageInfo pageInfo = new PageInfo();
  
  public PageResponse() {
    pageData = new ArrayList<>();
  }
  
  public PageResponse(List<T> pageData, PageInfo pageInfo) {
    this.pageData = pageData;
    this.pageInfo = pageInfo;
  }
  
  
  public List<T> getPageData() {
    return pageData;
  }
  public void setPageData(List<T> pageData) {
    this.pageData = pageData;
  }
  public PageInfo getPageInfo() {
    return pageInfo;
  }
  public void setPageInfo(PageInfo pageInfo) {
    this.pageInfo = pageInfo;
  }
  
  public void setPage(Integer page) {
    pageInfo.setPage(page);
  }
  public void setSize(Integer size) {
    pageInfo.setSize(size);
  }
  public void setStart(Integer start) {
    pageInfo.setStart(start);
  }
  public void setEnd(Integer end) {
    pageInfo.setEnd(end);
  }
  public void setTotal(Integer total) {
    pageInfo.setTotal(total);
  }
  
  @JsonIgnore
  public Integer getPage() {
    return pageInfo.getPage();
  }
  @JsonIgnore
  public Integer getSize() {
    return pageInfo.getSize();
  }
  @JsonIgnore
  public Integer getStart() {
    return pageInfo.getStart();
  }
  @JsonIgnore
  public Integer getEnd() {
    return pageInfo.getEnd();
  }
  @JsonIgnore
  public Integer getTotal() {
    return pageInfo.getTotal();
  }
  
  @Override
  public int size() {
    return pageData.size();
  }

  @JsonIgnore
  @Override
  public boolean isEmpty() {
    return pageData.isEmpty();
  }

  @Override
  public boolean contains(Object o) {
    return pageData.contains(o);
  }

  @Override
  public Iterator<T> iterator() {
    return pageData.iterator();
  }

  @Override
  public Object[] toArray() {
    return pageData.toArray();
  }

  @Override
  public <T> T[] toArray(T[] a) {
    return pageData.toArray(a);
  }

  @Override
  public boolean add(T e) {
    return pageData.add(e);
  }

  @Override
  public boolean remove(Object o) {
    return pageData.remove(o);
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return pageData.containsAll(c);
  }

  @Override
  public boolean addAll(Collection<? extends T> c) {
    return pageData.addAll(c);
  }

  @Override
  public boolean addAll(int index, Collection<? extends T> c) {
    return pageData.addAll(index, c);
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    return removeAll(c);
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    return pageData.retainAll(c);
  }

  @Override
  public void clear() {
    pageData.clear();
  }

  @Override
  public T get(int index) {
    return pageData.get(index);
  }

  @Override
  public T set(int index, T element) {
    return pageData.set(index, element);
  }

  @Override
  public void add(int index, T element) {
    pageData.add(index, element);
  }

  @Override
  public T remove(int index) {
    return pageData.remove(index);
  }

  @Override
  public int indexOf(Object o) {
    return pageData.indexOf(o);
  }

  @Override
  public int lastIndexOf(Object o) {
    return pageData.lastIndexOf(o);
  }

  @Override
  public ListIterator<T> listIterator() {
    return pageData.listIterator();
  }

  @Override
  public ListIterator<T> listIterator(int index) {
    return pageData.listIterator(index);
  }

  @Override
  public List<T> subList(int fromIndex, int toIndex) {
    return pageData.subList(fromIndex, toIndex);
  }
}