package rentacar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="category")
public class Category {
	

	private int idCategory;
	private String CategoryType;
	
	public Category() {
		// TODO Auto-generated constructor stub
	}
	

	public Category(String categoryType) {
		//super();
		CategoryType = categoryType;
	}

	public Category(int idCategory, String categoryType) {
		super();
		this.idCategory = idCategory;
		CategoryType = categoryType;
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idCategory")
	public int getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(int idCategory) {
		this.idCategory = idCategory;
	}

	public String getCategoryType() {
		return CategoryType;
	}

	public void setCategoryType(String categoryType) {
		CategoryType = categoryType;
	}
	
	
}

