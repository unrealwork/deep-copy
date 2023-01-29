package org.ecwid.dev.examples.classes;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Man {
    private final int age;
    private final Set<Man> friends;
    private String name;
    private List<String> favoriteBooks;

    public Man(String name, int age, List<String> favoriteBooks) {
        this.name = name;
        this.age = age;
        this.favoriteBooks = favoriteBooks;
        this.friends = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Man man = (Man) o;
        return getAge() == man.getAge() && Objects.equals(getName(), man.getName()) && Objects.equals(getFavoriteBooks(), man.getFavoriteBooks());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAge(), getName(), getFavoriteBooks());
    }

    public Set<Man> getFriends() {
        return friends;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void addFriend(Man man) {
        this.friends.add(man);
    }

    public List<String> getFavoriteBooks() {
        return favoriteBooks;
    }

    public void setFavoriteBooks(List<String> favoriteBooks) {
        this.favoriteBooks = favoriteBooks;
    }

    @Override
    public String toString() {

        return "Man@" + objectRef(this) + "{" +
                "age=" + age +
                ", name@" + objectRef(name) + "='" + name + '\'' +
                ", favoriteBooks@" + objectRef(favoriteBooks) + "=" + favoriteBooks +
                '}';
    }

    private String objectRef(Object obj) {
        return Integer.toHexString(System.identityHashCode(obj));
    }

}
