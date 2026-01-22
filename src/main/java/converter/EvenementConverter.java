package converter;

import java.util.List;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.app.entity.Dispositif;



@FacesConverter(value = "evenementConverter")
public class EvenementConverter implements Converter {

    @SuppressWarnings("unchecked")
	@Override
    public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
        if (value == null || value.trim().isEmpty()) return null;

        try {
            Long id = Long.valueOf(value);

            // R�cup�rer la liste depuis le component si possible
            List<Dispositif> items = null;
            for (UIComponent child : component.getChildren()) {
                if (child instanceof UISelectItems) {
                    items = (List<Dispositif>) ((UISelectItems) child).getValue();
                    break;
                }
            }

            if (items != null) {
                return items.stream()
                    .filter(item -> item.getId().equals(id))
                    .findFirst()
                    .orElse(null);
            }

        } catch (NumberFormatException e) {
            return null;
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext ctx, UIComponent component, Object obj) {
        if (obj == null) return "";
        if (obj instanceof Dispositif) {
            return String.valueOf(((Dispositif) obj).getId());
        }
        return "";
    }
}
